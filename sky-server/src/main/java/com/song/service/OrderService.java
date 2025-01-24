package com.song.service;

import com.song.dto.OrdersSubmitDTO;
import com.song.vo.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO);
}
