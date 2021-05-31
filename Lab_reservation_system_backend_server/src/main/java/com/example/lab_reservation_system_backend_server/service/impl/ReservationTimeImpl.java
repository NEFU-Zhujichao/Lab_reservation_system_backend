package com.example.lab_reservation_system_backend_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lab_reservation_system_backend_server.mapper.ReservationTimeMapper;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import com.example.lab_reservation_system_backend_server.service.IReservationTime;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeImpl extends ServiceImpl<ReservationTimeMapper, ReservationTime> implements IReservationTime {
}
