package com.example.lab_reservation_system_backend_server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ApiModel(value="预约表对象", description="")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "教师id")
    private Long uid;

    @ApiModelProperty(value = "教师姓名")
    private String uname;

    @ApiModelProperty(value = "实验课程id")
    private Long cid;

    @ApiModelProperty(value = "课程名称")
    private String cname;

    @ApiModelProperty(value = "实验室名称")
    private String labName;

    @ApiModelProperty(value = "课程预约时间集合")
    @TableField(exist = false)
    @JsonIgnoreProperties
    private List<ReservationTime> reservationTimes;

}
