package com.sezioo.wechar_demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sezioo.wechar_demo.util.WlwHttpClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MediaService {
	
	public static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
	public static String DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	
	public String mediaUpload(File file,String type,String accessToken) throws Exception {
		String url = String.format(UPLOAD_URL,accessToken,type);
		log.info("media upload url:{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		String mediaReturn = httpClient.postFile(url, file);
		log.info("wechat return message:{}",mediaReturn);
		return mediaReturn;
	}
	
	public InputStream mediaDownload(String mediaId,String accessToken) throws Exception {
		String url = String.format(DOWNLOAD_URL, accessToken,mediaId);
		log.info("download media url:{}",url);
		WlwHttpClient httpClient = new WlwHttpClient(true);
		Map<String, String> headers = Maps.newHashMap();
		InputStream inputStream = httpClient.getStream(url, headers);
//		File file = new File("C:\\Users\\qinpeng\\Pictures\\wechat.jpg");
		return inputStream;
	}
	
	public static File downloadMedia(String fileName, String accessToken, String mediaId) {
        String path = String.format(DOWNLOAD_URL, accessToken,mediaId);;
        //return httpRequestToFile(fileName, url, "GET", null);

        if (fileName == null || path == null) {
            return null;
        }
        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
             URL url = new URL(path);
             conn = (HttpURLConnection) url.openConnection();
             conn.setDoOutput(true);
             conn.setDoInput(true);
             conn.setUseCaches(false);
             conn.setRequestMethod("GET");

             inputStream = conn.getInputStream();
             if (inputStream != null) {
                 file = new File(fileName);
             } else {
                 return file;
             }

             //写入到文件
             fileOut = new FileOutputStream(file);
             if (fileOut != null) {
            	 byte[] tempBytes = new byte[1024];
                 int c = inputStream.read(tempBytes);
                 while (c != -1) {
                     fileOut.write(tempBytes,0,c);
                     c = inputStream.read(tempBytes);
                 }
             }
        } catch (Exception e) {
        } finally {
             if (conn != null) {
                 conn.disconnect();
             }

             try {
                  inputStream.close();
                  fileOut.close();
               } catch (IOException execption) {
             }
        }
    return file;
    }
	
	public static void main(String[] args) {
		MediaService.downloadMedia("C:\\Users\\qinpeng\\Pictures\\test1.jpg",
				"15_S4c6_EEIZYMWdMdFsEq4l_BcaR6D35qY6Wfqkr_-e86b32CtfurpVXWgLKz9K_rrTOi7sklrfVmeEWzL0lioys6Dld_jhXqaVIGy3Qly3wWO3BNRGfI4DSQ8QHoOh4PLtOycI4vgkfZXxu0TGBWeAGAJEZ",
				"1oKuI1iGyBW528UsJH_2VGEQABH_Dh_SNKrERTnpjwuTPiwV8nrJolkRKAlUoHpb");
	}

}
