package com.example.lab_reservation_system_backend_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lab_reservation_system_backend_server.pojo.Lab;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NEFU_Chao
 * @since 2021-05-15
 */
public interface ILabService extends IService<Lab> {

    /**
     * 基于课程id查询实验室信息
     * @param id
     * @return
     */
    RespBean getLabById(Long id);
}
