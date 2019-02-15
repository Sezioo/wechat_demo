package com.sezioo.wechat_demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatProperty {
	private String appId;
	private String appsecret;
	private String token;
	private String authUrl;
	private String authRedirectUrl;
	private String menuUrl;
	
}
