package com.sezioo.wechat_demo.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWatcher implements Watcher {

	private static final Logger log = LoggerFactory.getLogger(MyWatcher.class);

	@Override
	public void process(WatchedEvent event) {
		log.info("触发Watcher，节点路径:{}",event.getPath());
	}

}
