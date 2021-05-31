package com.example.lab_reservation_system_backend_server.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "教师id")
    private Long uid;

    @ApiModelProperty(value = "实验课程id")
    private Long cid;

    @ApiModelProperty(value = "实验室名字")
    private String labName;

    @ApiModelProperty(value = "实验周次")
    private Integer week;

    @ApiModelProperty(value = "实验星期")
    private Integer day;

    @ApiModelProperty(value = "实验节数")
    private Integer section;
}
