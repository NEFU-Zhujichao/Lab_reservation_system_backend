package com.example.lab_reservation_system_backend_server.service.impl;

import com.example.lab_reservation_system_backend_server.pojo.User;
import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
