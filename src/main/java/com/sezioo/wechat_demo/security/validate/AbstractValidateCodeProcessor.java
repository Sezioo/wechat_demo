package com.sezioo.wechat_demo.security.validate;

import com.sezioo.wechat_demo.commons.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @ClassName AbstractValidateCodeProcessor
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/11 11:30
 * @Version 1.0
 **/
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String,CodeGenerator> codeGeneratorMap;

    @Override
    public JsonData process(ServletWebRequest webRequest) {
        C validateCode = generate(webRequest);
        save(validateCode, webRequest);
        send(validateCode,webRequest);
        return JsonData.success();
    }

    public C generate(ServletWebRequest webRequest) {
        String type = StringUtils.substringAfter(webRequest.getRequest().getRequestURI(), "/code/");
        type = type + "CodeGenerator";
        CodeGenerator codeGenerator = codeGeneratorMap.get(type);
        if (codeGenerator == null) {
            throw new ValidateAuthenticationException("不存在对应的验证码生成器");
        }
        C validateCode = (C)codeGenerator.generate(webRequest);
        return validateCode;
    }

    public void save(C validateCode,ServletWebRequest webRequest) {
        ValidateCode code = new ValidateCode();
        BeanUtils.copyProperties(validateCode, code);
        webRequest.getRequest().getSession().setAttribute(validateCode.getClass().getSimpleName(),code);
    }

    public abstract void send(C validateCode,ServletWebRequest webRequest);
}
