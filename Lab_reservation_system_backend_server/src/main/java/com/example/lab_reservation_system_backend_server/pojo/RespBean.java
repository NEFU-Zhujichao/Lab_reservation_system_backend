package com.example.lab_reservation_system_backend_server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "统一返回对象",description = "")
public class RespBean {

    @ApiModelProperty(value = "HTTP状态码")
    private int code;

    @ApiModelProperty(value = "返回的友好消息")
    private String message;

    @ApiModelProperty(value = "返回的数据对象")
    private Object data;

    public static RespBean success(String message,Object data){
        return new RespBean(200,message,data);
    }

    public static RespBean error(int code,String message){
        return new RespBean(code,message,null);
    }

}
