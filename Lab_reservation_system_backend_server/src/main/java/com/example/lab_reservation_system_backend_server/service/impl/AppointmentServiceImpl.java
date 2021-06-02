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
import com.example.lab_reservation_system_backend_server.service.ICourseService;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private ICourseService courseService;
    @Autowired
    private ReservationTimeMapper reservationTimeMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 基于实验室名字查询实验室预约情况
     * @param name
     * @return
     */
    @Override
    public RespBean getAppointmentsByName(String name) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<Appointment> appointments = (List<Appointment>) valueOperations.get("AppointmentsByLab:"+name);
        if (CollectionUtils.isEmpty(appointments)){
            appointments = appointmentMapper.selectList(new QueryWrapper<Appointment>().eq("lab_name", name));
        }
        if (!CollectionUtils.isEmpty(appointments)){
            appointments.forEach(appointment -> {
                List<ReservationTime>  reservationTimes = (List<ReservationTime>) valueOperations
                        .get("reservationTimes:uid=" + appointment.getUid() + " cid=" + appointment.getCid() + " lab_name=" + appointment.getLabName());
                if (CollectionUtils.isEmpty(reservationTimes)){
                    reservationTimes = reservationTimeMapper.selectList(new QueryWrapper<ReservationTime>()
                            .eq("uid",appointment.getUid())
                            .eq("cid",appointment.getCid())
                            .eq("lab_name",appointment.getLabName()));
                    valueOperations.set("reservationTimes:uid=" + appointment.getUid() + " cid=" + appointment.getCid() + " lab_name=" + appointment.getLabName(),reservationTimes);
                }
                appointment.setReservationTimes(reservationTimes);
            });
            valueOperations.set("AppointmentsByLab:"+name,appointments);
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
            redisTemplate.delete("AppointmentsByLab:"+appointment.getLabName());
            redisTemplate.delete("Teacher:"+appointment.getUid()+" course:"+appointment.getCid()+"SurplusPeriods");
            redisTemplate.delete("reservationTimes:uid=" + appointment.getUid() + " cid=" + appointment.getCid() + " lab_name=" + appointment.getLabName());
            return RespBean.success("预约成功",null);
        }catch (Exception e){
            return RespBean.error(500,"预约失败");
        }
    }

    /**
     * 查询该教师剩余可约学分数
     * @return
     */
    @Override
    public RespBean getSurplusPeriods(Long id) {

        Course course = (Course) courseService.getCourseById(id).getData();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer surplusPeriods = (Integer) valueOperations.get("Teacher:"+UserUtils.getCurrentUser().getId()+" course:"+id+"SurplusPeriods");
        if (surplusPeriods == null){
            List<ReservationTime> reservationTimes = reservationTimeMapper.selectList(new QueryWrapper<ReservationTime>()
                    .eq("uid", UserUtils.getCurrentUser().getId())
                    .eq("cid", id));
            surplusPeriods = (course.getPeriods() - reservationTimes.size() * 2);
            valueOperations.set("Teacher:"+UserUtils.getCurrentUser().getId()+" course:"+id+"SurplusPeriods",surplusPeriods);
        }
        return RespBean.success(null,surplusPeriods);
    }
}
