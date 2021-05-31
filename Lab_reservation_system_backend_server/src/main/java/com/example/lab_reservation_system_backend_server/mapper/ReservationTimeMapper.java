package com.example.lab_reservation_system_backend_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReservationTimeMapper extends BaseMapper<ReservationTime> {
}
