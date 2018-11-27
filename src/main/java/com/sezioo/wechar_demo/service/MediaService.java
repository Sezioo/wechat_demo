package com.sezioo.wechar_demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sezioo.wechar_demo.util.RedisUtil;
import com.sezioo.wechar_demo.util.WlwHttpClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MediaService {
	
	public static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
	public static String DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	
	@Autowired
	private RedisUtil redisUtil;
	
	public String mediaUpload(File file,String type) throws Exception {
		String accessToken = (String) redisUtil.get("wechat_access_token");
		String url = String.format(UPLOAD_URL,accessToken,type);
		log.info("media upload url:{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		String mediaReturn = httpClient.postFile(url, file);
		log.info("wechat return message:{}",mediaReturn);
		return mediaReturn;
	}
	
	/**
	 * 获取文件
	 * @param mediaId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public File mediaDownload(String mediaId,String accessToken) throws Exception {
		String url = String.format(DOWNLOAD_URL, accessToken,mediaId);
		log.info("download media url:{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		Map<String, String> headers = Maps.newHashMap();
		File file = httpClient.getFile(url, headers);
		return file;
	}
	
	/**
	 * 直接将文件流写入到HttpResponse的输出流当中
	 * @param mediaId
	 * @param accessToken
	 * @throws Exception
	 */
	public void fileDownload(String mediaId,String accessToken) throws Exception {
		String url = String.format(DOWNLOAD_URL, accessToken,mediaId);
		log.info("download media url:{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		Map<String, String> headers = Maps.newHashMap();
		httpClient.getFile1(url, headers);
	}
	

}
