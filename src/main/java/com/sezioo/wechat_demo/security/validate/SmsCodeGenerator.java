package com.sezioo.wechat_demo.security.validate;

import com.sezioo.wechat_demo.security.config.SecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName SmsCodeGenerator
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/27 9:49
 * @Version 1.0
 **/
public class SmsCodeGenerator implements CodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest webRequest) {
        String randomNumeric = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        SmsValidateCode validateCode = new SmsValidateCode(randomNumeric, securityProperties.getCode().getSms().getExpires());
        return validateCode;
    }
}
