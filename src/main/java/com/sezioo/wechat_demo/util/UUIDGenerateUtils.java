package com.sezioo.wechat_demo.util;

import java.util.UUID;

public class UUIDGenerateUtils {
	
	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

}
