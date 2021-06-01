package com.example.lab_reservation_system_backend_server.controller;

import com.example.lab_reservation_system_backend_server.pojo.Course;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@RestController
@RequestMapping("/api/course")
@Api(tags = "实验课程Controller")
public class CourseController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "根据教师id查询实验课程")
    @GetMapping("/teacher/{id}")
    public RespBean getCoursesByTeacherId(@PathVariable Long id){
        return courseService.getCoursesByTeacherId(id);
    }

    @ApiOperation(value = "查询全部实验课程")
    @GetMapping("/")
    public RespBean getAllCourses(){
        return courseService.getAllCourses();
    }

    @ApiOperation(value = "根据课程id查询实验课程")
    @GetMapping("/{id}")
    public RespBean getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @ApiOperation(value = "添加实验课程")
    @PostMapping(value = "/")
    public RespBean addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @ApiOperation(value = "移除实验课程")
    @DeleteMapping(value = "/{id}")
    public RespBean deleteCourse(@PathVariable Long id){
        if (courseService.removeById(id)){
            redisTemplate.delete("courses");
            return RespBean.success("删除成功",null);
        }
        return RespBean.error(500,"删除失败");
    }

    @ApiOperation(value = "修改实验课程信息")
    @PutMapping(value = "/")
    public RespBean updateCourse(@RequestBody Course course){
        if (courseService.updateById(course)){
            redisTemplate.delete("courses");
            return RespBean.success("修改成功",null);
        }
        return RespBean.error(500,"修改失败");
    }
}
