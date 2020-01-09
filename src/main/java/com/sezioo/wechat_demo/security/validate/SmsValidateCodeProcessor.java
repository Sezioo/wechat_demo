package com.sezioo.wechat_demo.security.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @ClassName SmsValidateCodeProcessor
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/16 14:10
 * @Version 1.0
 **/
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<SmsValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    public void send(SmsValidateCode validateCode, ServletWebRequest webRequest) {
        smsCodeSender.send(validateCode.getCode(), webRequest.getParameter("mobile"));
    }
}
