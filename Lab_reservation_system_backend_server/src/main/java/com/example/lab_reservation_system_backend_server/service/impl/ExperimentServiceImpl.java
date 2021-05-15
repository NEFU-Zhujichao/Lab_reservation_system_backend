package com.example.lab_reservation_system_backend_server.service.impl;

import com.example.lab_reservation_system_backend_server.pojo.Experiment;
import com.example.lab_reservation_system_backend_server.mapper.ExperimentMapper;
import com.example.lab_reservation_system_backend_server.service.IExperimentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ExperimentServiceImpl extends ServiceImpl<ExperimentMapper, Experiment> implements IExperimentService {

}
