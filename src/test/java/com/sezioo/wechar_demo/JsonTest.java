package com.sezioo.wechar_demo;

import org.junit.Test;

import com.sezioo.wechar_demo.dto.WechatUserInfo;
import com.sezioo.wechar_demo.dto.WechatUserToken;
import com.sezioo.wechar_demo.util.JsonMapper;

public class JsonTest {

	
	public static void main(String[] args) {
		String jsonStr = "{\"openid\":\"od3N-5m7P7zmTwdrNfuKkOPfEs_Q\",\"nickname\":\"Sezioo\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"重庆\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/Q0j4TwGTfTKdf2RMnv2wTMBfze9nBOSB4mzNsbbtPmbnmQFko82OCvtYzJvmseoxB5P8arBsCoTLiaofdgMJIbQ\\/132\",\"privilege\":[]}";

		
		WechatUserInfo wechatUserToken = JsonMapper.parse(jsonStr, WechatUserInfo.class);
		System.out.println(JsonMapper.obj2String(wechatUserToken));
	}
	
	@Test
	public void test() {
		String path = this.getClass().getResource("/").getPath();
		System.out.println(path);
	}
}
