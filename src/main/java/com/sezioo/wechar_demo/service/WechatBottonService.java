package com.sezioo.wechar_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sezioo.wechar_demo.property.WechatProperty;

@Service
public class WechatBottonService {
	
	private static String URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	@Autowired
	private WechatProperty wechatProperty;
	
	public String flushMenu() {
		
		
		return null;
	}
}
