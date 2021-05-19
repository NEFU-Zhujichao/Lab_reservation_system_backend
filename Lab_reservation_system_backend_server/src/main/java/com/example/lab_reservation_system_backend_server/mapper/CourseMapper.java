package com.example.lab_reservation_system_backend_server.mapper;

import com.example.lab_reservation_system_backend_server.dto.TeacherDTO;
import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询全部实验课程
     * @return
     */
    List<TeacherDTO> getAllCourses();
}
