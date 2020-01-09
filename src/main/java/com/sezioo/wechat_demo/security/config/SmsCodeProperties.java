package com.sezioo.wechat_demo.security.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SmsCodeProperties
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/27 9:57
 * @Version 1.0
 **/
@Getter
@Setter
public class SmsCodeProperties {
    private int length = 6;
    private int expires = 60;
    String url = "/authentication/mobile";
}
