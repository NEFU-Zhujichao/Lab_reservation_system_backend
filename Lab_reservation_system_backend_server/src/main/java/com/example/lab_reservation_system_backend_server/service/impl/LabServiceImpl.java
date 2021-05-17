package com.example.lab_reservation_system_backend_server.service.impl;

import com.example.lab_reservation_system_backend_server.mapper.LabMapper;
import com.example.lab_reservation_system_backend_server.pojo.Lab;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.service.ILabService;
import org.springframework.stereotype.Service;

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

}
