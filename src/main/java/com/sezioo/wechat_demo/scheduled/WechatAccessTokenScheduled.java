package com.sezioo.wechat_demo.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sezioo.wechat_demo.property.WechatProperty;
import com.sezioo.wechat_demo.util.RedisUtil;
import com.sezioo.wechat_demo.util.WlwHttpClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WechatAccessTokenScheduled {
	
	@Autowired
	private WechatProperty wechatProperty;
	
	@Autowired
	private RedisUtil redisUtil;
	
//	@Scheduled(fixedRate = 1000*60*90)
	public void getWechatAccessToken() {
		try {
			WlwHttpClient httpClient = new WlwHttpClient();
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
			urlBuilder.append(wechatProperty.getAppId());
			urlBuilder.append("&secret=");
			urlBuilder.append(wechatProperty.getAppsecret());
			String url = urlBuilder.toString();
			String accessToken = httpClient.get(url);
			log.info("accessTocken:{}",accessToken);
			JSONObject tokenObject = JSONObject.parseObject(accessToken);
			Object token = tokenObject.get("access_token");
			redisUtil.set("wechat_access_token", token, 60*90);
		} catch (Exception e) {
			log.error("获取accessToken失败",e);
		}
	}
}
