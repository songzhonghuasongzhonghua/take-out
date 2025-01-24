package com.song.controller.user;

import com.song.constant.StatusConstant;
import com.song.entity.Dish;
import com.song.result.Result;
import com.song.service.DishService;
import com.song.vo.DishVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        String key = "dish_"+categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        //如果从redis中读取到菜品数据，则直接使用redis的数据
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLED);//查询起售中的菜品

       list = dishService.listWithFlavor(dish);
       //如果缓存中未读取到数据，则重新往缓存中设置值
       redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }

}
