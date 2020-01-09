package com.sezioo.wechat_demo.security.auth;

import com.sezioo.wechat_demo.commons.JsonData;
import com.sezioo.wechat_demo.security.config.SecurityProperties;
import com.sezioo.wechat_demo.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SecurityAuthenticationFailureHandler
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 14:17
 * @Version 1.0
 **/
@Slf4j
@Configuration("authenticationFailureHandler")
public class SecurityAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getSingInResponseType())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonMapper.obj2String(JsonData.fail(exception.getMessage())));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
