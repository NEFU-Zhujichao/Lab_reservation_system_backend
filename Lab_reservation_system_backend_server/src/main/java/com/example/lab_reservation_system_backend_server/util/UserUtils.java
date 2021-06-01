package com.example.lab_reservation_system_backend_server.util;

import com.example.lab_reservation_system_backend_server.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class UserUtils {

    /**
     * 获取当前登录用户
     * @return
     */
    public static User getCurrentUser(){
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    /**
     * 判断当前角色是否拥有ADMIN角色权限
     * @return
     */
    public static boolean hasAdminRole(){
        long count = getCurrentUser().getRoles().stream()
                .filter(role -> "ADMIN".equals(role.getName()))
                .count();
        return count > 0;
    }
}
