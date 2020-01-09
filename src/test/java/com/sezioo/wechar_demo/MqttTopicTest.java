package com.sezioo.wechar_demo;

import com.sezioo.wechat_demo.util.WlwHttpClient;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MqttTopicTest
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/10/26 16:03
 * @Version 1.0
 **/
public class MqttTopicTest {

    @Test
    public void publishTest(){
        String string = "F1D3A1000009641A5DB0166B00000178000000000000000002B039C057";
        String url = "http://api.heclouds.com/v1/synccmds/device_id=565328104&timeout=30";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "bBXzv7QBpihGPjpsCiv6BNFIpEwWcabWzvjw9VzvDxs=");
        WlwHttpClient client = new WlwHttpClient();
//        byte[] bytes = hexStringToByteArray(string);
//        HttpEntity httpEntity = new ByteArrayEntity(bytes);
        try {
            String post = client.post(url, string, headers);
            System.out.println(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
