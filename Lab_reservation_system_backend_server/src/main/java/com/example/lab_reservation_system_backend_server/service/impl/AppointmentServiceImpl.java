package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.mapper.CourseMapper;
import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.example.lab_reservation_system_backend_server.mapper.AppointmentMapper;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
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
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 基于实验室名字查询实验室预约情况
     * @param name
     * @return
     */
    @Override
    public RespBean getAppointmentsByName(String name) {
        List<Appointment> appointments = appointmentMapper.selectList(new QueryWrapper<Appointment>().eq("lab_name", name));
        if (appointments != null && appointments.size() > 0){
            return RespBean.success("查询成功",appointments);
        }
        return RespBean.error(500,"没有任何预约记录");
    }

    /**
     * 预约实验室
     * @param appointment
     * @return
     */
    @Override
    public RespBean orderLab(Appointment appointment) {
        try {
            // 查找是否含有 预约的星期和节数相同，而且结束周次大于此次预约开始周次的。 如果有记录则说明此次预约不成功，因为时间冲突了。
            List<Appointment> appointments = appointmentMapper.selectList(new QueryWrapper<Appointment>().
                    eq("section", appointment.getSection()).
                    eq("day", appointment.getDay()).
                    // 大于
                    gt("end_week", appointment.getWeek()));
            if (appointment != null && appointments.size() > 0){
                return RespBean.error(500,"存在时间冲突");
            }
            appointment.setUid(UserUtils.getCurrentUser().getId());
            appointment.setUname(UserUtils.getCurrentUser().getName());
            Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", appointment.getCid()));
            appointment.setCname(course.getName());
            appointment.setEndWeek(appointment.getWeek() + course.getPeriods() / 4);
            if (appointmentMapper.insert(appointment) > 0){
                return RespBean.success("预约成功",null);
            }
            return RespBean.error(500,"预约失败");
        }catch (Exception e){
            return RespBean.error(500,"预约失败");
        }
    }
}
