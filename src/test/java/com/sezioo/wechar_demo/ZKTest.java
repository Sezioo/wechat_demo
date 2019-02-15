package com.sezioo.wechar_demo;

import static org.assertj.core.api.Assertions.setLenientDateParsing;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sezioo.wechat_demo.DemoApplication;
import com.sezioo.wechat_demo.zk.CuratorClient;
import com.sezioo.wechat_demo.zk.MyWatcher;

import ch.qos.logback.core.pattern.parser.Node;

/**
 * 测试Curator
 * @author qinpeng
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=DemoApplication.class)
@WebAppConfiguration
public class ZKTest {
	
	@Autowired
	private CuratorClient curatorClient;
	
	private CuratorFramework client;
	
	private static final Logger log = LoggerFactory.getLogger(ZKTest.class);
	
	@Before
	public void init() {
		client = curatorClient.getClient();
		client.start();
	}
	
	@After
	public void destroy() {
		curatorClient.destroyClient(client);
	}
	
	@Test
	public void connectTest() {
		System.out.println(curatorClient.getClient().getState());
	}
	
	@Test
	/**
	 * 新增节点
	 * @throws Exception
	 */
	public void createNodeTest() throws Exception {
		String path = "/super/test/testChild";
		byte[] data = "this is a test message".getBytes();
		log.info("client state : [{}]",client.getState());
		String result = client.create().creatingParentsIfNeeded()
			.withMode(CreateMode.PERSISTENT)
			.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
			.forPath(path, data);
		log.info(result);
	}
	
	@Test
	/**
	 * 修改节点
	 * @throws Exception
	 */
	public void modifyNodeTest() throws Exception {
		String path = "/super/test";
		byte[] data = "this is a new test message".getBytes();
		Stat stat = client.setData().withVersion(0).forPath(path, data);
		log.info(stat.toString());
	}
	
	@Test
	/**
	 * 删除节点
	 * @throws Exception
	 */
	public void deleteNodeTest() throws Exception {
		String path = "/super/test-0000000000";
		client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
	}
	
	@Test
	/**
	 * 获取节点数据
	 * @throws Exception
	 */
	public void getNodeData() throws Exception {
		Stat stat = new Stat();
		String path = "/super/test";
		byte[] data = client.getData().storingStatIn(stat).forPath(path);
		log.info("节点[{}]的数据为[{}]",path,new String(data));
		log.info("该节点的版本号为[{}]",stat.getVersion());
	}
	
	@Test
	/**
	 * 查询子节点
	 * @throws Exception
	 */
	public void getChildrenTest() throws Exception {
		String path = "/super";
		List<String> childrenNodes = client.getChildren().forPath(path);
		childrenNodes.forEach(node -> {
			log.info("子节点[{}]",node);
		});
	}
	
	@Test
	/**
	 * 监听节点
	 * @throws Exception
	 */
	public void watcherTest() throws Exception {
		String path = "/super/test";
		client.getData().usingWatcher(new MyWatcher()).forPath(path);
		Thread.sleep(100000);
	}
	
	@SuppressWarnings("resource")
	@Test
	/**
	 * 监听N次节点的数据变化
	 * @throws Exception
	 */
	public void nodeListenerTest() throws Exception {
		String path = "/super/test";
		NodeCache nodeCache = new NodeCache(client, path);
		nodeCache.start(true);
		if (nodeCache.getCurrentData().getData() == null) {
			log.info("节点初始化数据为空");
		}else {
			log.info("节点初始化数据为：{}",new String(nodeCache.getCurrentData().getData()));
		}
		
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				if(nodeCache.getCurrentData().getData() != null) {
					log.info("{}的节点数据发生变化，最新的数据为{}",nodeCache.getCurrentData().getPath()
							,new String(nodeCache.getCurrentData().getData()));
				}else {
					log.info("获取节点数据发生错误，无法获取缓存的数据节点，可能已经被删除");
				}
			}
		});
		
		Thread.sleep(200000);
		
		curatorClient.destroyClient(client);
	}
	
	@Test
	public void childrenListenerTest() throws Exception {
		String path = "/super/test";
		final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
		pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				log.info("监听成功");
				if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
					log.info("\n---------------------------------\n");
					log.info("子节点初始化成功");
				}else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
					log.info("\n---------------------------------\n");
					log.info("子节点添加成功,path={},data={}",event.getData().getPath(),new String(event.getData().getData()));
				}else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
					log.info("\n---------------------------------\n");
					log.info("子节点删除成功,path={}",event.getData().getPath());
				}else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
					log.info("\n---------------------------------\n");
					log.info("子节点更新成功,path={},data={}",event.getData().getPath(),new String(event.getData().getData()	));
				}
			}
		});
		
		Thread.sleep(200000);
		pathChildrenCache.close();
		curatorClient.destroyClient(client);
	}
	
}
