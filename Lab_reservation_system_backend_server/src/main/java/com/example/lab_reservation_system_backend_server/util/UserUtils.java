package com.example.lab_reservation_system_backend_server.util;

import com.example.lab_reservation_system_backend_server.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static User getCurrentUser(){
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
