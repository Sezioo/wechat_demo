package com.sezioo.wechat_demo.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SecurityProperties
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/21 11:42
 * @Version 1.0
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@ConfigurationProperties(prefix = "sezioo.security")
@Getter
@Setter
public class SecurityProperties {
    BrowserProperties browser = new BrowserProperties();
    CodeProperties code = new CodeProperties();
}
