package com.sezioo.wechat_demo.security.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName CaptchaCodeProperties
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/22 16:44
 * @Version 1.0
 **/
@Setter
@Getter
public class CaptchaCodeProperties {
    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expires = 60;
    private String url = "/security/code/captcha";
}
