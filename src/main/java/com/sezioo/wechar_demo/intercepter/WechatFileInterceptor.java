package com.sezioo.wechar_demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sezioo.wechar_demo.commons.ResponseHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WechatFileInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ResponseHolder.addStream(response.getOutputStream());
		log.info("拦截成功！");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ResponseHolder.remove();
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ResponseHolder.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
