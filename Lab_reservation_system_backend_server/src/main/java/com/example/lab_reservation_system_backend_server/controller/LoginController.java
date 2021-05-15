package com.example.lab_reservation_system_backend_server.controller;


import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.example.lab_reservation_system_backend_server.pojo.UserLoginObject;
import com.example.lab_reservation_system_backend_server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginObject userLoginObject,HttpServletRequest request){
        return userService.login(userLoginObject.getUsername(),userLoginObject.getPassword(),request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/getCurrentUser")
    public User getCurrentUser(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        if (principal == null) return null;
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username);
        // 防止用户密码泄露，不需要将密码传到前端
        user.setPassword(null);
        return user;
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功",null);
    }
}
