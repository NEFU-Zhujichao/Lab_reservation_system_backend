package com.example.lab_reservation_system_backend_server.controller;

import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.IReservationTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservationTime")
@Api(tags = "预约时间Controller")
public class ReservationTimeController {

    @Autowired
    private IReservationTime reservationTime;

    @ApiOperation(value = "获取预约时间")
    @GetMapping("/")
    public RespBean getReservationTimes(){
        if(reservationTime.list().size() > 0){
            return RespBean.success("null",reservationTime.list());
        }
        return RespBean.success("没有任何预约时间",null);
    }
}
