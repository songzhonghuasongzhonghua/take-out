package com.song.service;

import com.song.vo.OrderReportVO;
import com.song.vo.SalesTop10ReportVO;
import com.song.vo.TurnoverReportVO;
import com.song.vo.UserReportVO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    TurnoverReportVO getTurnoverStatistic(LocalDate start, LocalDate end);

    UserReportVO getUserStatistic(LocalDate start, LocalDate end);

    OrderReportVO getOrderStatistic(LocalDate start, LocalDate end);

    SalesTop10ReportVO getTop10(LocalDate start, LocalDate end);
}
