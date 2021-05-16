package com.example.lab_reservation_system_backend_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map hello(){
        return Map.of("msg","hello test");
    }
}
