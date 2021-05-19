package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lab_reservation_system_backend_server.dto.TeacherDTO;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.mapper.CourseMapper;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.ICourseService;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 添加实验课程
      * @param course
     * @return
     */
    @Override
    public RespBean addCourse(Course course) {
        try {
            course.setUid(UserUtils.getCurrentUser().getId());
            if (courseMapper.insert(course) > 0){
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
        List<Course> courses = courseMapper.selectList(new QueryWrapper<Course>().eq("uid", id));
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setCourses(courses);
        teacherDTO.setUser(UserUtils.getCurrentUser());
        if (teacherDTO != null)
        return RespBean.success("查询成功",teacherDTO);
        return RespBean.error(500,"查询失败");
    }

    /**
     * 查询全部实验课程
     * @return
     */
    @Override
    public RespBean getAllCourses() {
        List<TeacherDTO> teacherDTOS = courseMapper.getAllCourses();
        if (teacherDTOS != null && teacherDTOS.size() > 0){
            return RespBean.success("查询成功",teacherDTOS);
        }
        return RespBean.error(500,"查询失败");
    }
}
