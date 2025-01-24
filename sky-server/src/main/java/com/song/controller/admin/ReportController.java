package com.song.controller.admin;

import com.song.result.Result;
import com.song.service.ReportService;
import com.song.vo.OrderReportVO;
import com.song.vo.SalesTop10ReportVO;
import com.song.vo.TurnoverReportVO;
import com.song.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/turnover")
    public Result<TurnoverReportVO> getTrunoverStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("获取营业额 :{} {}", start, end);
        TurnoverReportVO turnoverStatistic = reportService.getTurnoverStatistic(start, end);
        return Result.success(turnoverStatistic);
    }


    @GetMapping("/userStatistic")
    public Result<UserReportVO> getUserStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        log.info("获取用户量：{} {}", start, end);
        UserReportVO userReportVO = reportService.getUserStatistic(start,end);

        return Result.success(userReportVO);
    }

    @GetMapping("/orderStatistic")
    public Result<OrderReportVO> getOrderStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        log.info("获取订单量：{} {}", start, end);
        OrderReportVO orderReportVO = reportService.getOrderStatistic(start,end);

        return Result.success(orderReportVO);
    }

    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> getTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        log.info("获取销量前10商品量：{} {}", start, end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.getTop10(start,end);
        return Result.success(salesTop10ReportVO);
    }


}
