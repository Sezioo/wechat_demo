package com.sezioo.wechat_demo.security.session;

import com.sezioo.wechat_demo.commons.JsonData;
import com.sezioo.wechat_demo.util.JsonMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @ClassName MyExpiredSessionStrategy
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/29 11:08
 * @Version 1.0
 **/
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(JsonMapper.obj2String(JsonData.fail("用户在其它地方登录")));
    }
}
