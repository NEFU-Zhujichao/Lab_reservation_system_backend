package com.example.lab_reservation_system_backend_server.controller;


import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.IAppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@RestController
@RequestMapping("/api/appointment")
@Api(tags = "预约信息Controller")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @ApiOperation(value = "基于实验室名字查询实验室预约情况")
    @GetMapping("/{name}")
    public RespBean getAppointmentsByName(@PathVariable String name){
        return appointmentService.getAppointmentsByName(name);
    }

    @ApiOperation(value = "预约实验室")
    @PostMapping("/")
    public RespBean orderLab(@RequestBody Appointment appointment){
        return appointmentService.orderLab(appointment);
    }
}
