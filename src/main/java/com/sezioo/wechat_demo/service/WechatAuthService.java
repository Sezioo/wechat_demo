package com.sezioo.wechat_demo.service;

import java.net.URLEncoder;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sezioo.wechat_demo.dto.WechatUserInfo;
import com.sezioo.wechat_demo.dto.WechatUserToken;
import com.sezioo.wechat_demo.property.WechatProperty;
import com.sezioo.wechat_demo.util.JsonMapper;
import com.sezioo.wechat_demo.util.RedisUtil;
import com.sezioo.wechat_demo.util.WlwHttpClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WechatAuthService {
	
	private static String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect";
	private static String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=%s";
	private static String TOKEN_REFRESH_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=%s&refresh_token=%s";
	private static String USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";
	private static String TOKEN_CHECK_URL="https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
	
	@Autowired
	private WechatProperty wechatProperty;
	
	@Autowired
	private RedisUtil redisUtil;
	
	public JSONObject pageAuth() throws Exception {
		String redirectUrl = "http://ikyxuf.natappfree.cc/wechat/pageAuthRedirect";
		String encodeRedirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
		String url = String.format(AUTH_URL,wechatProperty.getAppId(),encodeRedirectUrl,"snsapi_userinfo" );
		WlwHttpClient httpClient = new WlwHttpClient(true);
		JSONObject jsonObject = httpClient.getJSON(url);
		log.info("wechat page auth : ",jsonObject);
		return jsonObject;
	}
	
	public WechatUserToken getUserGrantToken(String code,String grantType) throws Exception {
		String url = String.format(TOKEN_URL, wechatProperty.getAppId(),wechatProperty.getAppsecret(),code,grantType);
		log.info("发起HTTP请求获取用户TOKEN：{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		String userAccessToken = httpClient.get(url);
		WechatUserToken userToken = JsonMapper.string2Obj(userAccessToken,new TypeReference<WechatUserToken>() {
		});
		String key = "user_token_"+userToken.getOpenId();
		redisUtil.set(key, userToken, userToken.getExpiresIn()*2);
		log.info("获取到用户TOKEN:{}",userToken);
		log.info("branch dev test");
		return userToken;
	}
	
	public WechatUserToken refreshToken(String grantType,String refreshToken) throws Exception {
		String url = String.format(TOKEN_REFRESH_URL, wechatProperty.getAppId(),grantType,refreshToken);
		log.info("更新用户Token...");
		WlwHttpClient httpClient = new WlwHttpClient(true);
		String userAccessToken = httpClient.get(url);
		WechatUserToken userToken = JsonMapper.string2Obj(userAccessToken,new TypeReference<WechatUserToken>() {
		});
		String key = "user_token_"+userToken.getOpenId();
		redisUtil.set(key, userToken, userToken.getExpiresIn()*2);
		log.info("更新用户Token成功:{}",userToken);
		return userToken;
	}
	
	public WechatUserInfo getUserInfo(String accessToken,String opengId,String lang) throws Exception {
		String url = String.format(USER_URL,accessToken,opengId, lang);
		log.info("获取用户信息...");
		WlwHttpClient httpClient = new WlwHttpClient(true);
		String userAccessToken = httpClient.get(url);
		WechatUserInfo userInfo = JsonMapper.string2Obj(userAccessToken,new TypeReference<WechatUserInfo>() {
		});
		String key = "user_info_"+userInfo.getOpenId();
		redisUtil.set(key, userInfo, 60*60*24);
		log.info("获取用户信息成功:{}",userInfo);
		return userInfo;
	}
	
	public WechatUserInfo getUserInfo(String openId) throws Exception {
		String userKey = "user_info_"+openId;
		WechatUserInfo userInfo = (WechatUserInfo)redisUtil.get(userKey);
		if(userInfo != null)
			return userInfo;
		String tokenKey = "user_token_" + openId;
		WechatUserToken userToken = (WechatUserToken) redisUtil.get(tokenKey);
		if(userToken == null)
			throw new RuntimeException("授权失效请重新授权");
		boolean checkAccessToken = checkAccessToken(userToken.getAccessToken(),userToken.getOpenId());
		if(!checkAccessToken) {
			userToken = refreshToken("snsapi_userinfo", userToken.getRefreshToken());
		}
		return getUserInfo(userToken.getAccessToken(), userToken.getOpenId(), "zh_CN");
	}
	
	public boolean checkAccessToken(String accessToken,String openId) throws Exception {
		String url = String.format(TOKEN_CHECK_URL, accessToken,openId);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		JSONObject jsonObject = httpClient.getJSON(url);
		log.info("校验accessToken是否有效:{}",jsonObject);
		return jsonObject.getInteger("errcode") == 0;
	}
	

}
