package com.example.lab_reservation_system_backend_server.service;

import com.example.lab_reservation_system_backend_server.mapper.AppointmentMapper;
import com.example.lab_reservation_system_backend_server.mapper.ReservationTimeMapper;
import com.example.lab_reservation_system_backend_server.pojo.Appointment;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@Rollback(value = false)
public class AppointmentServiceTest {

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private ReservationTimeMapper reservationTimeMapper;

    @Test
    public void test_addAppointment(){
        Appointment appointment = new Appointment();
        appointment.setUid(1L);
        appointment.setUname("hello");
        appointment.setCname("java");
        appointment.setCid(2L);
        appointment.setLabName("903");
        ReservationTime reservationTime = new ReservationTime();
        reservationTime.setWeek(1);
        reservationTime.setDay(1);
        reservationTime.setSection(1);
        //List<ReservationTime> list = new ArrayList<>();
        //list.add(reservationTime);
        //reservationTimeMapper.insert(reservationTime);
        //appointment.setReservationTimes(list);
        appointmentMapper.insert(appointment);
    }
}
