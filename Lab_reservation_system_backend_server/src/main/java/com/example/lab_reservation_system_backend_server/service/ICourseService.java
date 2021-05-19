package com.example.lab_reservation_system_backend_server.service;

import com.example.lab_reservation_system_backend_server.pojo.Course;
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
public interface ICourseService extends IService<Course> {

    /**
     * 添加实验课程
     * @param course
     * @return
     */
    RespBean addCourse(Course course);

    /**
     * 根据教师id查询实验课程
     * @param id
     * @return
     */
    RespBean getCoursesByTeacherId(Long id);

    /**
     * 查询全部实验课程
     * @return
     */
    RespBean getAllCourses();
}
