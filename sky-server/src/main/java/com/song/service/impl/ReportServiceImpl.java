package com.song.service.impl;

import com.song.dto.GoodsSalesDTO;
import com.song.entity.Orders;
import com.song.mapper.OrderDetailMapper;
import com.song.mapper.OrderMapper;
import com.song.mapper.UserMapper;
import com.song.service.ReportService;
import com.song.vo.OrderReportVO;
import com.song.vo.SalesTop10ReportVO;
import com.song.vo.TurnoverReportVO;
import com.song.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 获取营业额统计数据
     * @param start
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistic(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(start);

        while (!start.equals(end)) {
            start = start.plusDays(1);
            dateList.add(start);
        }

        List<Double> turnoverStatisticList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0 : turnover;
            turnoverStatisticList.add(turnover);

        }
//        String dateListString = String.join(",", dateList.toString());
//        String turnoverListString = String.join(",",turnoverStatisticList.toString());

        String turnoverListString = StringUtils.join(turnoverStatisticList, ",");
        String dateListString = StringUtils.join(dateList, ",");

        return TurnoverReportVO.builder()
                .dateList(dateListString)
                .turnoverList(turnoverListString)
                .build();
    }

    /**
     * 获取用户量数据
     * @param start
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserStatistic(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(start);
        while (!start.equals(end)) {
            start = start.plusDays(1);
            dateList.add(start);
        }
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date,LocalTime.MAX);
            Map totalMap = new HashMap();
            Map newMap = new HashMap();

            newMap.put("startTime",startTime);
            newMap.put("endTime",endTime);
            totalMap.put("endTime",endTime);

            Integer totalUser = userMapper.countByMap(totalMap);
            Integer newUser = userMapper.countByMap(newMap);
            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
    }

    /**
     * 获取订单量数据
     * @param start
     * @param end
     * @return
     */
    @Override
    public OrderReportVO getOrderStatistic(LocalDate start, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(start);
        while (!start.equals(end)) {
            start = start.plusDays(1);
            dateList.add(start);
        }

        List<Integer> totalOrderList = new ArrayList<>();
        List<Integer> validOrderList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date,LocalTime.MAX);
            Integer totalOrder = getCountByMap(startTime, endTime, null);
            totalOrder = totalOrder == null ? 0 : totalOrder;

            Integer validOrder = getCountByMap(startTime, endTime, Orders.COMPLETED);
            validOrder = validOrder == null ? 0 : validOrder;

            totalOrderList.add(totalOrder);
            validOrderList.add(validOrder);
        }

        Integer totalOrderCount =totalOrderList.stream().reduce(Integer::sum).get();
        Integer validOrderCount =validOrderList.stream().reduce(Integer::sum).get();


        Double orderCompleteRate = 0.0;
        if(totalOrderCount != 0){
            orderCompleteRate = (double)totalOrderCount / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(totalOrderList,","))
                .validOrderCountList(StringUtils.join(validOrderList,","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompleteRate)
                .build();
    }

    /**
     * 获取销量前10商品
     * @param start
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO getTop10(LocalDate start, LocalDate end) {
        LocalDateTime startTime = LocalDateTime.of(start,LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end,LocalTime.MAX);
        List<GoodsSalesDTO> top10SalesList = orderDetailMapper.getTop10Sales(startTime, endTime);
        List<String> nameList = top10SalesList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = top10SalesList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }


    private Integer getCountByMap(LocalDateTime startTime,LocalDateTime endTime,Integer status){
        Map map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("status",status);
       return orderMapper.countByMap(map);

    }
}
