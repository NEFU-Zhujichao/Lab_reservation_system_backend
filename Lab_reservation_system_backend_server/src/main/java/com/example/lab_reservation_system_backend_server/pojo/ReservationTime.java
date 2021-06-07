package com.example.lab_reservation_system_backend_server.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "预约时间对象",description = "")
public class ReservationTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "教师id")
    private Long uid;

    @ApiModelProperty(value = "教师名字")
    private String uname;

    @ApiModelProperty(value = "实验课程id")
    private Long cid;

    @ApiModelProperty(value = "实验课程名字")
    private String cname;

    @ApiModelProperty(value = "实验室名字")
    private String labName;

    @ApiModelProperty(value = "实验周次")
    private Integer week;

    @ApiModelProperty(value = "实验星期")
    private Integer day;

    @ApiModelProperty(value = "实验节数")
    private Integer section;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;
}
