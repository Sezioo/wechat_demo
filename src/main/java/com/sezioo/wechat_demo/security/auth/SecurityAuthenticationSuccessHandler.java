package com.sezioo.wechat_demo.security.auth;

import com.sezioo.wechat_demo.commons.JsonData;
import com.sezioo.wechat_demo.security.config.SecurityProperties;
import com.sezioo.wechat_demo.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SecurityAuthenticationSuccessHandler
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 11:32
 * @Version 1.0
 **/
@Slf4j
@Component("successAuthenticationHandler")
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private SecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();

    private SavedRequest savedRequest;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSingInResponseType())) {
            response.setContentType("application/json;charset=utf-8");
            String type = authentication.getClass().getSimpleName();
            response.getWriter().write(JsonMapper.obj2String(JsonData.success(type)));
        } else {
            savedRequest = requestCache.getRequest(request, response);
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }
    }
}
