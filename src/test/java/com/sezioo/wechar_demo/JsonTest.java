package com.sezioo.wechar_demo;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import com.sezioo.wechat_demo.dto.WechatUserInfo;
import com.sezioo.wechat_demo.dto.WechatUserToken;
import com.sezioo.wechat_demo.util.JsonMapper;

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
	
	@Test
	public void arrayToStrTest() {
		String[] strings = {"we","are","one"};
		String str = Arrays.toString(strings);
		System.out.println(str);
	}
	
	@Test
	public void regTest() {
		String regex = "[^0-9]+";
		String src = "2019--11-5";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);
		while(matcher.find()) {
			System.out.println(matcher);
		}
	}
	
	@Test
	public void pathTest() {
		String path = "/222";
		String[] paths = path.split("/");
		System.out.println(paths.length);
		System.out.println(Arrays.deepToString(paths));
	}

	@Test
	public void randomTest(){
        String randomNumeric = RandomStringUtils.randomNumeric(6);
        System.out.println(randomNumeric);
	}

	@Test
	public void startStrTest(){
		String str1 = "/V3.5/api/v1/new/login";
		String str2 = "/V3.5/api/v1/new/login";
		System.out.println(str1.startsWith(str2));
	}

}
