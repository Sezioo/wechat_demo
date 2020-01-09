package com.sezioo.wechat_demo.security.config;

import com.sezioo.wechat_demo.security.auth.LoginResponseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName BrowserProperties
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 11:45
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class BrowserProperties {
    private LoginResponseType singInResponseType = LoginResponseType.JSON;

    private int rememberMeSeconds = 3600;
}
