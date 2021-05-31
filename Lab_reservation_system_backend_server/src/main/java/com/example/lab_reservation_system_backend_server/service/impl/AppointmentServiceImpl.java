package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.mapper.CourseMapper;
import com.example.lab_reservation_system_backend_server.mapper.ReservationTimeMapper;
import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.example.lab_reservation_system_backend_server.mapper.AppointmentMapper;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.service.IReservationTime;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Service
@Transactional
@Slf4j
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ReservationTimeMapper reservationTimeMapper;

    /**
     * 基于实验室名字查询实验室预约情况
     * @param name
     * @return
     */
    @Override
    public RespBean getAppointmentsByName(String name) {
        List<Appointment> appointments = appointmentMapper.selectList(new QueryWrapper<Appointment>().eq("lab_name", name));
        if (appointments != null && appointments.size() > 0){
            appointments.forEach(appointment -> {
                List<ReservationTime> reservationTimes = reservationTimeMapper.selectList(new QueryWrapper<ReservationTime>()
                        .eq("uid",appointment.getUid())
                        .eq("cid",appointment.getCid())
                        .eq("lab_name",appointment.getLabName()));
                appointment.setReservationTimes(reservationTimes);
            });
            return RespBean.success("查询成功",appointments);
        }
        return RespBean.success("没有任何预约记录",null);
    }

    /**
     * 预约实验室
     * @param appointment
     * @return
     */
    @Override
    public RespBean orderLab(Appointment appointment) {
        try {
            appointment.setUid(UserUtils.getCurrentUser().getId());
            appointment.setUname(UserUtils.getCurrentUser().getName());
            Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", appointment.getCid()));
            appointment.setCname(course.getName());
            List<ReservationTime> reservationTimes = reservationTimeMapper.selectList(new QueryWrapper<ReservationTime>()
                    .eq("uid", appointment.getUid())
                    .eq("cid", appointment.getCid()));
            if(reservationTimes.size() >= (course.getPeriods() / 2)){
                return RespBean.error(500,"已选实验学时已满，请联系教务处添加实验学时");
            }
            appointment.getReservationTimes().forEach(reservationTime -> {
                reservationTime.setUid(UserUtils.getCurrentUser().getId());
                reservationTime.setCid(course.getId());
                reservationTime.setLabName(appointment.getLabName());
                reservationTimeMapper.insert(reservationTime);
          }
            );
            appointmentMapper.insert(appointment);
            return RespBean.success("预约成功",null);
        }catch (Exception e){
            return RespBean.error(500,"预约失败");
        }
    }
}
