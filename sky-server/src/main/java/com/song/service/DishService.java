package com.song.service;

import com.song.dto.DishDTO;
import com.song.dto.DishPageQueryDTO;
import com.song.entity.Dish;
import com.song.result.PageResult;
import com.song.vo.DishVO;

import java.util.List;

public interface DishService {

    void insertWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getById(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    List<Dish> getByCategoryId(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
