package com.example.lab_reservation_system_backend_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private int code;
    private String message;
    private Object data;

    public static RespBean success(String message,Object data){
        return new RespBean(200,message,data);
    }

    public static RespBean error(int code,String message){
        return new RespBean(code,message,null);
    }

}
