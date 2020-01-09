package com.sezioo.wechat_demo.security.config;

import com.sezioo.wechat_demo.security.auth.MyUserDetailsService;
import com.sezioo.wechat_demo.security.auth.SecurityAuthenticationSuccessHandler;
import com.sezioo.wechat_demo.security.filter.CaptchaFilter;
import com.sezioo.wechat_demo.security.filter.SmsCodeValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/15 10:01
 * @Version 1.0
 **/
@Configuration
public class SecurityBrowserConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAuthenticationSuccessHandler successAuthenticationHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private SmsCodeValidateFilter smsCodeValidateFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionExpiredStrategy;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/security/requires")
                .loginProcessingUrl("/authentication/form")
                .successHandler(successAuthenticationHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/security/sessionInvalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(sessionExpiredStrategy)
                .and()
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(myUserDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/security/formLogin", "/authentication/**", "/security/code/**","/security/sessionInvalid").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .apply(securityConfig)

        ;
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("123456").roles("admin");
    }*/
}
