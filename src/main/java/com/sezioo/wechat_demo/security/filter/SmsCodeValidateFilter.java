package com.sezioo.wechat_demo.security.filter;

import com.google.common.collect.Sets;
import com.sezioo.wechat_demo.security.config.SecurityProperties;
import com.sezioo.wechat_demo.security.validate.CaptchaValidateCode;
import com.sezioo.wechat_demo.security.validate.SmsValidateCode;
import com.sezioo.wechat_demo.security.validate.ValidateAuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashSet;

/**
 * @ClassName CaptchaFilter
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 15:06
 * @Version 1.0
 **/
@Component
public class SmsCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       /* if (!StringUtils.equals("/authentication/form", request.getRequestURI()) ||
                !StringUtils.equalsIgnoreCase("post", request.getMethod())
        ) {
            filterChain.doFilter(request,response);
            return;
        }
        ServletWebRequest webRequest = new ServletWebRequest(request);
        String captcha = webRequest.getParameter("captcha");
        if (StringUtils.isNotBlank(captcha)) {
           CaptchaValidateCode cacheCaptcha = (CaptchaValidateCode) request.getSession().getAttribute("captcha");
           if (cacheCaptcha.getExpire().isBefore(LocalTime.now())){
               authenticationFailureHandler.onAuthenticationFailure(request,response,new ValidateAuthenticationException("验证码失效"));
           } else if (StringUtils.equalsIgnoreCase(captcha, cacheCaptcha.getCode())) {
               filterChain.doFilter(request,response);
           } else {
               authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateAuthenticationException("验证码错误"));
           }
        }else{
            authenticationFailureHandler.onAuthenticationFailure(request,response,new ValidateAuthenticationException("验证码为空"));
        }*/

        String urlStr = securityProperties.getCode().getSms().getUrl();
        String[] urls = StringUtils.split(urlStr, ",");
        HashSet<String> urlSet = Sets.newHashSet();
        boolean matched = false;
        for (String url : urls) {
            if (pathMatcher.match(url,request.getRequestURI())) {
                matched = true;
                break;
            }
        }
        if (matched && StringUtils.equalsIgnoreCase(HttpMethod.POST.name(), request.getMethod())) {
            ServletWebRequest webRequest = new ServletWebRequest(request);
            String captchaCode = webRequest.getParameter("smsValidateCode");
            SmsValidateCode smsValidateCode = (SmsValidateCode) request.getSession().getAttribute("smsValidateCode");
            if (smsValidateCode == null) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateAuthenticationException("验证码为空"));
                return;
            } else if (smsValidateCode.getExpire().isBefore(LocalTime.now())) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateAuthenticationException("验证码过期"));
                return;
            } else if (!StringUtils.equalsIgnoreCase(captchaCode, smsValidateCode.getCode())) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateAuthenticationException("验证码错误"));
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

}
