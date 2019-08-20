package com.sezioo.wechat_demo.security.controllers;

import com.sezioo.wechat_demo.commons.JsonData;
import com.sezioo.wechat_demo.util.JsonMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName BroswerSecurityController
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/20 15:33
 * @Version 1.0
 **/

@Controller
@RequestMapping("/security")
public class BrowserSecurityController {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private SavedRequest savedRequest;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping("/requires")
    @ResponseBody
    public JsonData requiresAnthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();
        if (targetUrl.endsWith(".html")) {
            redirectStrategy.sendRedirect(request,response,"/formLogin.html");
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

}
