package com.sezioo.wechar_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sezioo.wechar_demo.intercepter.WechatFileInterceptor;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public WechatFileInterceptor wechatFileInterceptor() {
		return new WechatFileInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(wechatFileInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
