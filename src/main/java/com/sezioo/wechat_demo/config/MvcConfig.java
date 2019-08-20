package com.sezioo.wechat_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.sezioo.wechat_demo.intercepter.RequestShowInterceptor;
import com.sezioo.wechat_demo.intercepter.WechatFileInterceptor;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public WechatFileInterceptor wechatFileInterceptor() {
		return new WechatFileInterceptor();
	}
	
	@Bean
	/**
	 * 参数打印
	 * @return
	 */
	public RequestShowInterceptor requestShowInterceptor() {
		return new RequestShowInterceptor();
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/views");
		viewResolver.setSuffix(".html");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(wechatFileInterceptor()).addPathPatterns("/wechat/fileDownload");
		registry.addInterceptor(requestShowInterceptor()).addPathPatterns("/wechat/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index1").setViewName("index1");
		registry.addViewController("/share").setViewName("share");
		WebMvcConfigurer.super.addViewControllers(registry);
	}
	
	
	
	
}
