package com.example.lab_reservation_system_backend_server.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lab_reservation_system_backend_server.pojo.ReservationTime;
import com.example.lab_reservation_system_backend_server.pojo.RespBean;
import com.example.lab_reservation_system_backend_server.pojo.RestPageBean;
import com.example.lab_reservation_system_backend_server.service.IReservationTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/reservationTime")
@Api(tags = "预约时间Controller")
public class ReservationTimeController {

    @Autowired
    private IReservationTime reservationTime;

    @ApiOperation(value = "获取预约时间")
    @GetMapping("/")
    public RestPageBean getReservationTimes(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer size){
        // 开启分页功能
        Page<ReservationTime> page = new Page<>(currentPage,size);
        // 返回带有分页的对象列表
        Page<ReservationTime> timePage = reservationTime.page(page);
        return new RestPageBean(timePage.getTotal(),timePage.getRecords());
    }

    @ApiOperation(value = "导出预约信息")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportReservationTime(HttpServletResponse response){
        List<ReservationTime> list = reservationTime.list();
        // 03版Excel兼容性更强
        ExportParams params = new ExportParams("预约信息","预约信息", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, ReservationTime.class, list);
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            // 流形式传输
            response.setHeader("content-type","application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("预约信息.xls","UTF-8"));
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
