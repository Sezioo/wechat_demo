package com.sezioo.wechat_demo.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicaContextHelper implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
	
	public static <T> T popBean(Class<T> clazz) {
		if(applicationContext == null)
			return null;
		return applicationContext.getBean(clazz);
	}
	
	public static <T> T popBean(String name,Class<T> clazz) {
		if(applicationContext == null)
			return null;
		return applicationContext.getBean(name, clazz);
	}
	
	

}
