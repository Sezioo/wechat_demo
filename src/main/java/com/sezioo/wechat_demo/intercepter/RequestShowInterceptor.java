package com.sezioo.wechat_demo.intercepter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.sezioo.wechat_demo.util.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 参数打印
 * @author qinpeng
 *
 */
public class RequestShowInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		Map<String, String[]> parameterMap = request.getParameterMap();
		log.info("requestURI:{},params:{}",requestURI,JsonMapper.obj2String(parameterMap));
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	
}
