package com.sezioo.wechar_demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sezioo.wechar_demo.commons.ResponseHolder;
import com.sezioo.wechar_demo.dto.WechatUserInfo;
import com.sezioo.wechar_demo.dto.WechatUserToken;
import com.sezioo.wechar_demo.param.LinkParam;
import com.sezioo.wechar_demo.property.WechatProperty;
import com.sezioo.wechar_demo.service.MediaService;
import com.sezioo.wechar_demo.service.MessageService;
import com.sezioo.wechar_demo.service.WechatAuthService;
import com.sezioo.wechar_demo.service.WechatBottonService;
import com.sezioo.wechar_demo.util.JsonMapper;
import com.sezioo.wechar_demo.util.SHA1Utils;
import com.sezioo.wechar_demo.util.WlwHttpClient;

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
	
	@Autowired
	private WechatBottonService wechatBottonService;
	
	@Autowired
	private WechatAuthService wechatAuthService;
	
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
	public String fileUpload() throws Exception {
		File file = new File("C:\\Users\\qinpeng\\Pictures\\test2.jpg");
		return mediaService.mediaUpload(file, "image");
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
	
	@RequestMapping("/addMenu")
	@ResponseBody
	public String addMenu() throws UnsupportedEncodingException {
		return wechatBottonService.addMenu();
	}
	
	/**
	 * 微信页面授权
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pageAuth")
	@ResponseBody
	public JSONObject pageAuth() throws Exception {
		log.info("进入微信页面授权");
		JSONObject jsonObject = wechatAuthService.pageAuth();
		log.info("微信页面授权成功");
		return jsonObject;
	}
	
	/**
	 * 微信授权跳转
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pageAuthRedirect")
	@ResponseBody
	public String pageAuthRedirect(@RequestParam(name="code") String code,@RequestParam("state") String state) throws Exception {
		log.info("pageAuthRedirect,code={},state={}",code,state);
		WechatUserToken userGrantToken = wechatAuthService.getUserGrantToken(code, "authorization_code");
		WechatUserInfo userInfo = wechatAuthService.getUserInfo(userGrantToken.getOpenId());
		return JsonMapper.obj2String(userInfo);
	}
	
	@RequestMapping("/index")
	public String index(Model model) throws UnsupportedEncodingException {
		String encodeRedirectUrl = URLEncoder.encode(wechatProperty.getAuthRedirectUrl(), "UTF-8");
		String urlMenu = String.format(wechatProperty.getAuthUrl(),wechatProperty.getAppId(),encodeRedirectUrl,"snsapi_userinfo" );
		model.addAttribute("redirectUrl", urlMenu);
		return "index";
	}
}
