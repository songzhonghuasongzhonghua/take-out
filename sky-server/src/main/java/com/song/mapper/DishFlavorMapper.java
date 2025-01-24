package com.song.mapper;

import com.song.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insert(List<DishFlavor> dishFlavors);

    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);


    void deleteByDishIds(List<Long> ids);


    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
