package com.example.lab_reservation_system_backend_server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.RestPageBean;
import com.example.lab_reservation_system_backend_server.service.IReservationTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservationTime")
@Api(tags = "预约时间Controller")
public class ReservationTimeController {

    @Autowired
    private IReservationTime reservationTime;

    @ApiOperation(value = "获取预约时间")
    @GetMapping("/")
    public RestPageBean getReservationTimes(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer size){
        // 开启分页功能
        Page<ReservationTime> page = new Page<>(currentPage,size);
        // 返回带有分页的对象列表
        Page<ReservationTime> timePage = reservationTime.page(page);
        return new RestPageBean(timePage.getTotal(),timePage.getRecords());
    }
}
