package com.sezioo.wechat_demo.security.config;

import com.sezioo.wechat_demo.security.validate.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ValidateCodeConfig
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/22 17:15
 * @Version 1.0
 **/
@Configuration
public class ValidateCodeConfig {
    @Bean
    @ConditionalOnMissingBean(name = "captchaGenerator")
    public CodeGenerator captchaGenerator(){
        return new CaptchaCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public CodeGenerator smsCodeGenerator(){
        return new SmsCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }


}
