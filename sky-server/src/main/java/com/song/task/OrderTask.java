package com.song.task;

import com.song.entity.Orders;
import com.song.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时未支付订单
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void processTimeoutOrder(){
        log.info("处理超时未支付订单");
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        ordersList.forEach(order -> {
            order.setStatus(Orders.CANCELLED);
            order.setCancelReason("订单超时未支付");
            order.setCancelTime(LocalDateTime.now());
            //todo 更新订单状态置为已取消
        });
    }


    /**
     * 处理派送中订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder(){
        log.info("处理派送中的订单");
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        ordersList.forEach(order -> {
            order.setStatus(Orders.COMPLETED);
            //todo 更新订单状态置于已完成
        });

    }
}
