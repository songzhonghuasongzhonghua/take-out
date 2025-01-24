package com.song.service.impl;

import com.song.constant.MessageConstant;
import com.song.context.BaseContext;
import com.song.dto.OrdersSubmitDTO;
import com.song.entity.AddressBook;
import com.song.entity.OrderDetail;
import com.song.entity.Orders;
import com.song.entity.ShoppingCart;
import com.song.exception.AddressBookBusinessException;
import com.song.exception.ShoppingCartBusinessException;
import com.song.mapper.AddressBookMapper;
import com.song.mapper.OrderDetailMapper;
import com.song.mapper.OrderMapper;
import com.song.mapper.ShoppingCartMapper;
import com.song.service.OrderService;
import com.song.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 提交订单
     * @param orderSubmitDTO
     * @return
     */
    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO) {
        //查看地址信息是否存在
        Long addressBookId = orderSubmitDTO.getAddressBookId();
        AddressBook addressBook = addressBookMapper.getById(addressBookId);
        if(addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }


        //查看购物车是否为空
        Long userId = BaseContext.getThreadLocal();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if(shoppingCartList == null || shoppingCartList.size() == 0){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //往订单表插入一条数据
        Orders order = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO, order);
        //当前时间戳
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setStatus(Orders.PENDING_PAYMENT);
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setPayStatus(Orders.UN_PAID);
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());

        orderMapper.insert(order);
        List<OrderDetail> orderDetailList = new ArrayList<>();

        //往订单详情表插入n条数据
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        //清空购物车

        shoppingCartMapper.deleteByUserId(userId);

        //返回vo信息
        OrderSubmitVO submitVO = OrderSubmitVO.builder()
                .orderAmount(order.getAmount())
                .orderNumber(order.getNumber())
                .orderTime(order.getOrderTime())
                .build();

        return submitVO;
    }
}
