package com.example.lab_reservation_system_backend_server.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.Role;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lab_reservation_system_backend_server.pojo.UserLoginObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    RespBean login(String username,String password,String code,HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<Role> getRoles(Long userId);

    /**
     * 添加教师信息
     * @param user
     * @return
     */
    RespBean addUser(User user);

    /**
     * 教师离职
     * @param id
     * @return
     */
    RespBean deleteUser(Long id);
}
