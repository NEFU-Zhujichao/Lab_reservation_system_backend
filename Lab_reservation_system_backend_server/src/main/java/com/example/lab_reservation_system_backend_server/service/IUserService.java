package com.example.lab_reservation_system_backend_server.service;

import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lab_reservation_system_backend_server.pojo.UserLoginObject;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
public interface IUserService extends IService<User> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username,String password,HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
