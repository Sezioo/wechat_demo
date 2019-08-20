package com.sezioo.wechat_demo.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyUserDetailsService
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/15 9:44
 * @Version 1.0
 **/
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("security login");
        String password = passwordEncoder.encode("123456");
        log.info("username:{},passowrd:{}",s,password);
        return new User("admin", password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("xxx"));
    }

}
