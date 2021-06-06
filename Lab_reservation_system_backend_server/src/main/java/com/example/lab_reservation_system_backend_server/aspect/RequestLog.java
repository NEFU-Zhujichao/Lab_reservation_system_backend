package com.example.lab_reservation_system_backend_server.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//用于封装请求信息
public class RequestLog{
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
}
