package com.sezioo.wechat_demo.security.validate;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @ClassName CaptchaValidataCodeProcessor
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/16 16:12
 * @Version 1.0
 **/
@Component
public class CaptchaValidateCodeProcessor extends AbstractValidateCodeProcessor<CaptchaValidateCode> {
    @Override
    public void send(CaptchaValidateCode validateCode, ServletWebRequest webRequest) {
        try {
            ImageIO.write(validateCode.getImage(), "jpeg", webRequest.getResponse().getOutputStream());
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException("获取图片验证码失败", e);
        }
    }
}
