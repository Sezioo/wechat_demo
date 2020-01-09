package com.sezioo.wechat_demo.security.config;

import com.sezioo.wechat_demo.security.session.MyExpiredSessionStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @ClassName BeanConfig
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/29 11:15
 * @Version 1.0
 **/
@Configuration
public class BeanConfig {

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionExpiredStrategy(){
        return new MyExpiredSessionStrategy();
    }

}
