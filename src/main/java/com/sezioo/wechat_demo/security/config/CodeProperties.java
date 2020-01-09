package com.sezioo.wechat_demo.security.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName CodeProperties
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/22 16:49
 * @Version 1.0
 **/
@Setter
@Getter
public class CodeProperties {
    private CaptchaCodeProperties captcha = new CaptchaCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
}
