package com.example.lab_reservation_system_backend_server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Course对象", description="")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "实验课程名称")
    private String name;

    @ApiModelProperty(value = "实验课程学生数")
    private Integer studentNumber;

    @ApiModelProperty(value = "实验课时")
    private Integer periods;

    @ApiModelProperty(value = "授课教师id")
    private Long uid;

}
