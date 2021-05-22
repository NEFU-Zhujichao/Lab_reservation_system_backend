package com.example.lab_reservation_system_backend_server.service;

import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
public interface IAppointmentService extends IService<Appointment> {

    /**
     * 基于实验室名字查询实验室预约情况
     * @param name
     * @return
     */
    RespBean getAppointmentsByName(String name);

    /**
     * 预约实验室
     * @param appointment
     * @return
     */
    RespBean orderLab(Appointment appointment);
}
