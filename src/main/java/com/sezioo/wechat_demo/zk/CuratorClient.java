package com.sezioo.wechat_demo.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sezioo.wechat_demo.property.ZKProperty;

/**
 * 获取CuratorFramework,zk连接
 * @author qinpeng
 *
 */
@Component
public class CuratorClient {
	
	private static final Logger log = LoggerFactory.getLogger(CuratorClient.class);
	
	@Autowired
	private ZKProperty zkProperty;
	
	private CuratorFramework client;
	
	private RetryPolicy retryPolicy;
	
	public CuratorFramework getClient() {
		return getClient(zkProperty.getNamespace());
	}
	
	public CuratorFramework getClient(String namespace) {
		retryPolicy = new ExponentialBackoffRetry(zkProperty.getRetryMs(),zkProperty.getRetryTimes());
		return getClient(namespace, retryPolicy);
	}
	
	public CuratorFramework getClient(String namespace,RetryPolicy retryPolicy) {
		if(client==null) {
			synchronized (this) {
				if(client==null) {
					client = CuratorFrameworkFactory.builder()
							.connectString(zkProperty.getConnectString()).retryPolicy(retryPolicy)
							.sessionTimeoutMs(zkProperty.getSessionTimeoutMs()).namespace(zkProperty.getNamespace()).build();
					log.info("generate CuratorFramework success,server address :" + client.getZookeeperClient().getCurrentConnectionString());
				}
			}
		}
		return client;
	}
	
	public void destroyClient(CuratorFramework client) {
		if(client != null) {
			client.close();
		}
	}
	
	
}
