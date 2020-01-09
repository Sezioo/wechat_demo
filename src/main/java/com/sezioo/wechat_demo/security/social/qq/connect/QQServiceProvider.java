package com.sezioo.wechat_demo.security.social.qq.connect;

import com.sezioo.wechat_demo.security.social.qq.api.QQ;
import com.sezioo.wechat_demo.security.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @ClassName QQServiceProvider
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/19 15:44
 * @Version 1.0
 **/
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private static final String URL_GET_CODE = "https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_GET_TOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    public QQServiceProvider(String appId,String appSecret) {
        super(new OAuth2Template(appId,appSecret,URL_GET_CODE,URL_GET_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accesssToken) {
        return new QQImpl(accesssToken,appId);
    }
}
