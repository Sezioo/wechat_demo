package com.sezioo.wechat_demo.security.social.qq.connect;

import com.sezioo.wechat_demo.security.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @ClassName QQConectionFactory
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/19 16:05
 * @Version 1.0
 **/
public class QQConectionFactory extends OAuth2ConnectionFactory<QQ> {
    public QQConectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
