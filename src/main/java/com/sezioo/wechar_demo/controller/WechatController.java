package com.sezioo.wechar_demo.controller;

import java.util.Arrays;

import org.apache.catalina.startup.FailedContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sezioo.wechar_demo.param.LinkParam;
import com.sezioo.wechar_demo.property.WechatProperty;
import com.sezioo.wechar_demo.util.SHA1Utils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
	
	@Autowired
	private WechatProperty wechatProperty;
	
	@RequestMapping("/link")
	@ResponseBody
	public String link(LinkParam param) {
		log.info("获取参数成功:{}",param);
		String signature = param.getSignature();
		String echostr = param.getEchostr();
		String nonce = param.getNonce();
		String timestamp = param.getTimestamp();
		String[] strArray = {wechatProperty.getToken(),timestamp,nonce};
		Arrays.sort(strArray);
		StringBuilder stringBuilder = new StringBuilder();
		for(String str : strArray) {
			log.info("str:{}",str);
			stringBuilder.append(str);
		}
		String signatureSource = stringBuilder.toString();
		String signatureEncrypt = SHA1Utils.caculate(signatureSource);
		log.info("signatureEncrypt:{}",signatureEncrypt);
		if(signature.equals(signatureEncrypt)) {
			log.info("success");
			return echostr;
		}else {
			log.info("fail");
			return "fail";
		}
	}
}
