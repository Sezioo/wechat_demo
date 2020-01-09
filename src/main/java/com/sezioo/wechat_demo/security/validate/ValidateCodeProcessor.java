package com.sezioo.wechat_demo.security.validate;

import com.sezioo.wechat_demo.commons.JsonData;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName ValidateCodeProcessor
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/11 11:27
 * @Version 1.0
 **/
public interface ValidateCodeProcessor {

    /**
     * 处理验证码逻辑
     * @param webRequest
     * @return
     */
    JsonData process(ServletWebRequest webRequest);
}
