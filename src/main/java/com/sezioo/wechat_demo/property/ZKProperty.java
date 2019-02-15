package com.sezioo.wechat_demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="zk")
public class ZKProperty {
	
	private String connectString;
	
	private String namespace;
	
	private int sessionTimeoutMs;
	
	private int retryMs;
	
	private int retryTimes;
}
