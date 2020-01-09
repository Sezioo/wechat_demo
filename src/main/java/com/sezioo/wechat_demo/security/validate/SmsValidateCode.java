package com.sezioo.wechat_demo.security.validate;

/**
 * @ClassName SmsValidateCode
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/27 9:52
 * @Version 1.0
 **/
public class SmsValidateCode extends ValidateCode {
    public SmsValidateCode(String code, int expires) {
        super(code,expires);
    }
}
