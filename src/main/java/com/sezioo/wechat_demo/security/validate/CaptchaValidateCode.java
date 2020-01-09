package com.sezioo.wechat_demo.security.validate;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

/**
 * @ClassName CaptchaValidateCode
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 15:38
 * @Version 1.0
 **/
@Getter
@Setter
public class CaptchaValidateCode extends ValidateCode {

    private BufferedImage image;

    public CaptchaValidateCode(BufferedImage image, String code, int expire) {
        super(code,expire);
        this.image = image;
    }
}
