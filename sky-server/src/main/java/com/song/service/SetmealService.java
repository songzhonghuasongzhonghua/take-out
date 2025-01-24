package com.song.service;

import com.song.dto.SetmealDTO;
import com.song.entity.Setmeal;
import com.song.vo.DishItemVO;

import java.util.List;

public interface SetmealService {

    void insertWithDish(SetmealDTO setmealDTO);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);


    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
