package com.sezioo.wechat_demo.security.social.qq.api;

import com.sezioo.wechat_demo.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @ClassName QQImpl
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/17 21:02
 * @Version 1.0
 **/
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String userString = getRestTemplate().getForObject(url, String.class);
        QQUserInfo qqUserInfo = JsonMapper.parse(userString, QQUserInfo.class);
        return qqUserInfo;
    }
}
