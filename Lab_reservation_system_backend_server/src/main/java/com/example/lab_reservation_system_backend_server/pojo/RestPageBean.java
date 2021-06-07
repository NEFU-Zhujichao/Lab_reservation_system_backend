package com.example.lab_reservation_system_backend_server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "公共返回分页对象",description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPageBean {

    @ApiModelProperty(value = "返回的总条数")
    private Long total;

    @ApiModelProperty(value = "返回的数据集合")
    private List<?> data;
}
