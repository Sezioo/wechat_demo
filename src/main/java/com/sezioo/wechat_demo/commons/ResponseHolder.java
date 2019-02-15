package com.sezioo.wechat_demo.commons;

import java.io.OutputStream;

public class ResponseHolder {
	
	private static ThreadLocal<OutputStream> streamHolder = new ThreadLocal<>();
	
	public static void addStream(OutputStream outputStream) {
		streamHolder.set(outputStream);
	}
	
	public static OutputStream getStream() {
		return streamHolder.get();
	}
	
	public static void remove() {
		streamHolder.remove();
	}
	
}
