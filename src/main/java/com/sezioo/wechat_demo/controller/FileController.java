package com.sezioo.wechat_demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author qinpeng
 * @Date 2019/9/12 10:24
 * @Version 1.0
 **/
@Controller
@Slf4j
public class FileController {
    @RequestMapping("/filedownload")
    public void ocxDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream resourceInputStream = null;
        try{
            Resource resource = new ClassPathResource("images/test1.jpg");
//        response.setHeader();
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("1.jpg","UTF-8"));
            resourceInputStream = resource.getInputStream();
            ServletOutputStream responseOutputStream = response.getOutputStream();
            byte[] tempContent = new byte[2048];
            int readLen = -1;
            while ((readLen = resourceInputStream.read(tempContent)) != -1) {
                responseOutputStream.write(tempContent, 0, readLen);
            }
            responseOutputStream.flush();
            if (responseOutputStream != null) {
                responseOutputStream.close();
            }
        }catch (IOException e){
            log.error("打开文件失败",e);
        }finally {
            if (resourceInputStream != null) {
                resourceInputStream.close();
            }
        }
    }
}
