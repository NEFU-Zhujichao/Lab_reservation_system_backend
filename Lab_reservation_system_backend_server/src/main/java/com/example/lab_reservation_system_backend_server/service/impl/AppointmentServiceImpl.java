package com.example.lab_reservation_system_backend_server.service.impl;

import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.example.lab_reservation_system_backend_server.mapper.AppointmentMapper;
import com.example.lab_reservation_system_backend_server.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

}
