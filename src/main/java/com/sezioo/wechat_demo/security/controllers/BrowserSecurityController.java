package com.sezioo.wechat_demo.security.controllers;

import com.sezioo.wechat_demo.commons.JsonData;
import com.sezioo.wechat_demo.security.validate.*;
import com.sezioo.wechat_demo.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName BroswerSecurityController
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/20 15:33
 * @Version 1.0
 **/

@Controller
@RequestMapping("/security")
@Slf4j
public class BrowserSecurityController {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private SavedRequest savedRequest;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private CodeGenerator captchaGenerator;

    @Autowired
    private CodeGenerator smsCodeGenerator;

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    @RequestMapping("/requires")
    @ResponseBody
    public JsonData requiresAnthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:{}",targetUrl);
            if (targetUrl.endsWith(".html")) {
                redirectStrategy.sendRedirect(request,response,"/security/formLogin");
            }
        }
        return JsonData.success("请求的资源需要认证，请引用用户到登录页");
    }
    @RequestMapping("/user")
    @ResponseBody
    public String user(){
        User user = new User("test", "test", AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return JsonMapper.obj2String(user);
    }

    @RequestMapping("/formLogin")
    public String formLogin(){
        return "formLogin";
    }

    /*@RequestMapping("/code/captcha")
    public void loadCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        CaptchaValidateCode captchaValidateCode = (CaptchaValidateCode) captchaGenerator.generate(webRequest);
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaValidateCode);
        ImageIO.write(captchaValidateCode.getImage(), "JPEG", response.getOutputStream());
    }

    @RequestMapping("/code/sms")
    public void loadSmsValidateCode(HttpServletRequest request, HttpServletResponse response) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ValidateCode smsValidateCode = smsCodeGenerator.generate(webRequest);
        HttpSession session = request.getSession();
        session.setAttribute("smsValidateCode", smsValidateCode);
        log.info("短信验证码为:"+smsValidateCode.getCode());
    }*/

    @RequestMapping("/code/{type}")
    public void loadValidateCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorMap.get(type+"ValidateCodeProcessor");
        if (validateCodeProcessor != null) {
            validateCodeProcessor.process(new ServletWebRequest(request,response));
        }else {
            log.error("验证码类型不存在:{}",type);
            throw new InternalAuthenticationServiceException("验证码类型不存在：" + type);
        }
    }

    @RequestMapping("/sessionInvalid")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public JsonData sessionInvalid() {
        return JsonData.fail("登录失效");
    }

}
