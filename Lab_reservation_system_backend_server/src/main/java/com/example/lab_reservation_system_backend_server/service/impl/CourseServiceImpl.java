package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.dto.TeacherDTO;
import com.example.lab_reservation_system_backend_server.mapper.UserMapper;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.mapper.CourseMapper;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.User;
import com.example.lab_reservation_system_backend_server.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ICourseService courseService;

    /**
     * 添加实验课程
      * @param course
     * @return
     */
    @Override
    public RespBean addCourse(Course course) {
        try {
            if ((course.getName() == null || course.getName().equals(' ')) || (course.getStudentNumber() == null || course.getStudentNumber().equals(' ')) || (course.getPeriods() == null || course.getPeriods().equals(' '))) {
                return RespBean.error(400,"请完整填写课程信息，并且不要使用空格");
            }
            course.setUid(UserUtils.getCurrentUser().getId());
            if (courseMapper.insert(course) > 0){
                redisTemplate.delete("courses");
                redisTemplate.delete("CoursesByTeacher:"+UserUtils.getCurrentUser().getId());
                return RespBean.success("添加成功",null);
            }else {
                course.setUid(null);
                return RespBean.error(500,"添加失败");
            }
        }catch (Exception e){
            throw new RuntimeException("添加失败");
        }
    }

    /**
     * 根据教师id查询实验课程
     * @param id
     * @return
     */
    @Override
    public RespBean getCoursesByTeacherId(Long id) {
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            List<Course> courses = (List<Course>) valueOperations.get("CoursesByTeacher:"+id);
            if (CollectionUtils.isEmpty(courses)){
                courses = courseMapper.selectList(new QueryWrapper<Course>().eq("uid", id));
                valueOperations.set("CoursesByTeacher:"+id,courses);
            }
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setCourses(courses);
            teacherDTO.setId(UserUtils.getCurrentUser().getId());
            teacherDTO.setUsername(UserUtils.getCurrentUser().getUsername());
            teacherDTO.setName(UserUtils.getCurrentUser().getName());
            if (teacherDTO != null)
                return RespBean.success(null,teacherDTO);
            return RespBean.error(500,"查询失败");
        }catch (Exception e){
            return RespBean.error(500,"查询失败");
        }
    }

    /**
     * 查询全部实验课程
     * @return
     */
    @Override
    public RespBean getAllCourses() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<TeacherDTO> teacherDTOS = (List<TeacherDTO>) valueOperations.get("courses");
        if (CollectionUtils.isEmpty(teacherDTOS)){
            teacherDTOS = courseMapper.getAllCourses();
        }
        if (!CollectionUtils.isEmpty(teacherDTOS)){
            valueOperations.set("courses",teacherDTOS);
            return RespBean.success(null,teacherDTOS);
        }
        return RespBean.error(500,"查询失败");
    }

    /**
     * 根据课程id查询实验课程
     * @param id
     * @return
     */
    @Override
    public RespBean getCourseById(Long id) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Course course = (Course) valueOperations.get("course:"+id);
        if(course == null){
            course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", id));
        }
        if (course != null){
            valueOperations.set("course:"+id,course);
            return RespBean.success(null,course);
        }
        return RespBean.error(500,"没有该门课程");
    }
}
