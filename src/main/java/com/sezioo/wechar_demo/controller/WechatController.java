package com.sezioo.wechar_demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.FailedContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sezioo.wechar_demo.dto.BaseMessage;
import com.sezioo.wechar_demo.dto.TextMessage;
import com.sezioo.wechar_demo.param.LinkParam;
import com.sezioo.wechar_demo.property.WechatProperty;
import com.sezioo.wechar_demo.util.SHA1Utils;
import com.sezioo.wechar_demo.util.WlwHttpClient;
import com.sezioo.wechar_demo.util.XmlUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
	
	@Autowired
	private WechatProperty wechatProperty;
	
	@RequestMapping(value = "/link", method = RequestMethod.GET)
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
	
	@RequestMapping("/accessToken")
	@ResponseBody
	public String getAccessToken() throws Exception {
		WlwHttpClient httpClient = new WlwHttpClient();
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
	//APPID&secret=APPSECRET
		urlBuilder.append(wechatProperty.getAppId());
		urlBuilder.append("&secret=");
		urlBuilder.append(wechatProperty.getAppsecret());
		String url = urlBuilder.toString();
		String accessToken = httpClient.get(url);
		log.info("accessTocken:{}",accessToken);
		return accessToken;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/link", method = RequestMethod.POST)
	@ResponseBody
	public void messageReponse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		BufferedReader reader = request.getReader();
		StringBuilder messageBuilder = new StringBuilder();
		String tempStr;
		while((tempStr=reader.readLine())!=null) {
			messageBuilder.append(tempStr);
		}
		String message = messageBuilder.toString();
		log.info("接受到用户信息：{}",message);
		TextMessage receiveMessage = XmlUtils.xmlToBean(TextMessage.class, message);
		log.info(message);
		
		TextMessage responseMessage = new TextMessage();
		BeanUtils.copyProperties(receiveMessage, responseMessage);
		responseMessage.setFromUserName(receiveMessage.getToUserName());
		responseMessage.setToUserName(receiveMessage.getFromUserName());
		responseMessage.setContent("测试成功！");
		
		String xml = XmlUtils.beanToXml(responseMessage);
		PrintWriter printWriter = response.getWriter();
		printWriter.println(xml);
	}
}
