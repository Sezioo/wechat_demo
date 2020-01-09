package com.sezioo.wechat_demo.security.validate;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DefaultSmsCodeSender
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/16 14:46
 * @Version 1.0
 **/
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String msg, String... mobile) {
        if (mobile != null && mobile.length != 0) {
            for (String tel : mobile) {
                log.info("{}:{}", tel, msg);
            }
        } else {
            log.error("短信发送号码为空");
        }
    }
}
