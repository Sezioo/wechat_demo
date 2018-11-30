package com.sezioo.wechar_demo.service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sezioo.wechar_demo.dto.WechatButton;
import com.sezioo.wechar_demo.property.WechatProperty;
import com.sezioo.wechar_demo.util.JsonMapper;
import com.sezioo.wechar_demo.util.RedisUtil;
import com.sezioo.wechar_demo.util.WlwHttpClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WechatBottonService {
	
	private static String URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
	private static String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect";
	
	@Autowired
	private WechatProperty wechatProperty;
	
	@Autowired
	private RedisUtil redisUtil;
	
	
	public String addMenu() throws UnsupportedEncodingException {
		
		String accessToken = (String) redisUtil.get("wechat_access_token");
		String url = String.format(URL, accessToken);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		
		/*String redirectUrl = "http://ikyxuf.natappfree.cc/wechat/pageAuthRedirect";
		String encodeRedirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
		String urlMenu = String.format(AUTH_URL,wechatProperty.getAppId(),encodeRedirectUrl,"snsapi_userinfo" );*/
		
		String urlMenu = "http://rmjbdz.natappfree.cc/wechat/index";
		WechatButton wechatButton = WechatButton.builder().type("view").url(urlMenu).name("菜单").build();
		ArrayList<WechatButton> buttons = Lists.newArrayList(wechatButton);
		HashMap<Object, Object> buttonMap = Maps.newHashMap();
		buttonMap.put("button", buttons);
		String entityString = JsonMapper.obj2String(buttonMap);
		log.info("add menu : {}",entityString);
		try {
			String wechatReturn = httpClient.post(url, entityString);
			JSONObject wechatReturnObject = JSONObject.parseObject(wechatReturn);
			String errmsg = (String) wechatReturnObject.get("errmsg");
			log.info("创建微信菜单：{}",errmsg);
			return errmsg;
		} catch (Exception e) {
			log.error("创建菜单失败",e);
			throw new RuntimeException("创建菜单失败",e);
		}
		
	}
}
