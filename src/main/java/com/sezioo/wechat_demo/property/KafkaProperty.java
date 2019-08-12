package com.sezioo.wechat_demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="kfk")
@Component
public class KafkaProperty {
	
	private int maxTimes;
	
}
