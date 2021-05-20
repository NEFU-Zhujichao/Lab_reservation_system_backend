package com.example.lab_reservation_system_backend_server.dto;

import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.pojo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TeacherDTO")
public class TeacherDTO {

//    @ApiModelProperty(value = "老师对象")
//    private User user;
    @ApiModelProperty(value = "老师id")
    private Long id;

    @ApiModelProperty(value = "老师姓名")
    private String name;

    @ApiModelProperty(value = "老师用户名")
    private String username;

    @ApiModelProperty(value = "老师教授的实验课程")
    private List<Course> courses;
}
