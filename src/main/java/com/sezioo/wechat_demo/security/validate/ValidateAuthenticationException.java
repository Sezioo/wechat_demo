package com.sezioo.wechat_demo.security.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName ValidateAuthenticationException
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 16:52
 * @Version 1.0
 **/
public class ValidateAuthenticationException extends AuthenticationException {
    public ValidateAuthenticationException(String msg) {
        super(msg);
    }
}
