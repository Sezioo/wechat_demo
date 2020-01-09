package com.sezioo.wechat_demo.security.validate;

/**
 * @ClassName SmsCodeSender
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/16 14:37
 * @Version 1.0
 **/
public interface SmsCodeSender {
    void send(String msg,String ... mobile);
}
