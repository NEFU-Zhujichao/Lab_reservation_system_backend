package com.example.lab_reservation_system_backend_server.controller;


import com.example.lab_reservation_system_backend_server.pojo.Lab;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.service.ILabService;
import com.example.lab_reservation_system_backend_server.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
@RestController
@RequestMapping("/api/lab")
@Api(tags = "实验室Controller")
public class LabController {

    @Autowired
    private ILabService labService;

    @ApiOperation(value = "查询所有实验室信息")
    @GetMapping("/")
    public RespBean getAllExperiments(){
        if (UserUtils.hasAdminRole()){
            List<Lab> experiments = labService.list();
            if (experiments != null && experiments.size() > 0){
                return RespBean.success("查询成功",experiments);
            }else return RespBean.error(500,"没有任何实验室信息");
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "增添实验室信息")
    @PostMapping("/")
    public RespBean addExperiment(@RequestBody Lab lab){
        if (UserUtils.hasAdminRole()){
            if (labService.save(lab)){
                return RespBean.success("添加成功",null);
            }else return RespBean.error(500,"添加失败");
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "修改实验室信息")
    @PutMapping("/")
    public RespBean updateExperiment(@RequestBody Lab lab){
        if (UserUtils.hasAdminRole()){
            if (labService.updateById(lab)){
                return RespBean.success("修改成功",null);
            }else return RespBean.error(500,"修改失败");
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }

    @ApiOperation(value = "删除实验室信息")
    @DeleteMapping("/{id}")
    public RespBean deleteExperiment(@PathVariable Long id){
        if (UserUtils.hasAdminRole()){
            if (labService.removeById(id)){
                return RespBean.success("删除成功",null);
            }else return RespBean.error(500,"删除失败");
        }
        return RespBean.error(403,"无权限，请联系管理员");
    }
}
