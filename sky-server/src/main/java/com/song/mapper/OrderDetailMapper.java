package com.song.mapper;

import com.song.dto.GoodsSalesDTO;
import com.song.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {

    void insertBatch(List<OrderDetail> orderDetails);

    List<GoodsSalesDTO> getTop10Sales(LocalDateTime startTime, LocalDateTime endTime);
}
