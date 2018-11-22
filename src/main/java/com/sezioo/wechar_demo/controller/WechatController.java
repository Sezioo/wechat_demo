package com.sezioo.wechar_demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sezioo.wechar_demo.commons.ResponseHolder;
import com.sezioo.wechar_demo.dto.BaseMessage;
import com.sezioo.wechar_demo.dto.TextMessage;
import com.sezioo.wechar_demo.param.LinkParam;
import com.sezioo.wechar_demo.property.WechatProperty;
import com.sezioo.wechar_demo.service.MediaService;
import com.sezioo.wechar_demo.service.MessageService;
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
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * get请求用于服务器连接验证
	 * @param param
	 * @return
	 */
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
	/**
	 * 获取accesstoken
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * post请求由于接受用户消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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
		String xml = messageService.messageProcess(message);
		log.info("返回用户信息：{}",xml);
		PrintWriter printWriter = response.getWriter();
		printWriter.println(xml);
	}
	
	/**
	 * 上传素材
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam String accessToken) throws Exception {
		File file = new File("C:\\Users\\qinpeng\\Pictures\\test.jpg");
		return mediaService.mediaUpload(file, "image", accessToken);
	}
	
	/**
	 * 下载素材
	 * @param param
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/fileDownload")
	public void getFile(@RequestParam Map<String, String> param,HttpServletResponse response) throws Exception {
		if(MapUtils.isEmpty(param))
			return;
		String mediaId = param.get("mediaId");
		String accessToken = param.get("accessToken");
		mediaService.fileDownload(mediaId, accessToken);
		OutputStream outputStream = ResponseHolder.getStream();
		outputStream.flush();
	}
}
