package com.song.mapper;

import com.song.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealDishIdsByDishIds(List<Long> dishIds);

    void insertBatch(List<SetmealDish> setmealDishList);
}
