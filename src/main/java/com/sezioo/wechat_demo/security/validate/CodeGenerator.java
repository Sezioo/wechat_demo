package com.sezioo.wechat_demo.security.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName CodeGenerator
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/22 16:24
 * @Version 1.0
 **/
public interface CodeGenerator {

    public ValidateCode generate(ServletWebRequest webRequest);
}
