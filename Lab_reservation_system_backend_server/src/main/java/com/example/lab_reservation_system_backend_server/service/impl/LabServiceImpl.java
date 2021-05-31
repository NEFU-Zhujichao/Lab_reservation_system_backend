package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.mapper.CourseMapper;
import com.example.lab_reservation_system_backend_server.mapper.LabMapper;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.pojo.Lab;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.ILabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LabServiceImpl extends ServiceImpl<LabMapper, Lab> implements ILabService {

    @Autowired
    private LabMapper labMapper;
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 基于课程id查询实验室信息
     * @param id
     * @return
     */
    @Override
    public RespBean getLabById(Long id) {

        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", id));
        if (course != null){
            List<Lab> labs = labMapper.selectList(new QueryWrapper<Lab>().ge("number", course.getStudentNumber()));
            if (labs != null && labs.size() > 0){
                return RespBean.success("已成功过滤实验室机器数小于课程人数的实验室",labs);
            }
            return RespBean.error(500,"暂时没有能容纳该门实验课程学生数的实验室");
        }
        return RespBean.error(500,"没有该门课程");
    }
}
