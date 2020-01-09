package com.sezioo.wechat_demo.security.validate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @ClassName ValidateCode
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/8/27 9:33
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private LocalTime expire;

    public ValidateCode( String code, int expire) {
        this.code = code;
        this.expire = LocalTime.now().plusSeconds(expire);
    }
}
