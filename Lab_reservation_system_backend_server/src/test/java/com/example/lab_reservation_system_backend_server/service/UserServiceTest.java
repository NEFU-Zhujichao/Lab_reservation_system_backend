package com.example.lab_reservation_system_backend_server.service;

import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = false)
public class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_addUser(){
        User user = new User();
        user.setName("NEFU_Chao2");
        user.setUsername("admin2");
        user.setPassword(passwordEncoder.encode("123"));
        userMapper.insert(user);
    }
}
