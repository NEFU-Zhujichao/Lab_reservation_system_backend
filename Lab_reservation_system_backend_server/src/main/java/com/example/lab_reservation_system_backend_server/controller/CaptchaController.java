package com.example.lab_reservation_system_backend_server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@Api(tags = "验证码Controller")
@Slf4j
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "获取验证码")
    // produces = "image/jpeg" 配置之后接口文档不再是乱码
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        // 定义response输出类型为image/jpeg
        response.setDateHeader("Expires",0);
        // Set standard HTTP/1.1 no-cache headers
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers(use addHeader)
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        // Set standard HTTP/1.1 no-cache header
        response.setHeader("Pragma","no-cache");
        // Return a jpeg
        response.setContentType("image/jpeg");
        //--------------生成验证码------------------
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        log.debug("验证码为"+text);
        // 将验证码文本内容放入session
        request.getSession().setAttribute("captcha",text);
        // 根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        // 将需要关闭的资源放在try语句中 try语句执行完毕自动关闭资源
        try (ServletOutputStream outputStream = response.getOutputStream()){
            // 输出流输出文件格式为jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
