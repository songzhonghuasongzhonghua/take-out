package com.song.service.impl;

import com.song.context.BaseContext;
import com.song.dto.ShoppingCartDTO;
import com.song.entity.Dish;
import com.song.entity.Setmeal;
import com.song.entity.ShoppingCart;
import com.song.mapper.DishMapper;
import com.song.mapper.SetmealMapper;
import com.song.mapper.ShoppingCartMapper;
import com.song.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getThreadLocal());
        //先查找购物车表，看是否已经存在数据
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果存在数据，则更新数量
        if(list != null && list.size() > 0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
            return;
        }
        //如果不存在数据，则插入数据
        Long dishId = shoppingCartDTO.getDishId();
        if(dishId != null){
            //插入菜品数据至购物车
            Dish dish = dishMapper.getById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setImage(dish.getImage());
        }else{
            //插入套餐数据至购物车
            Long setmealId = shoppingCartDTO.getSetmealId();
            Setmeal setmeal = setmealMapper.getById(setmealId);
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setImage(setmeal.getImage());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }


    @Override
    public List<ShoppingCart> getShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getThreadLocal());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void clean() {
        Long userId = BaseContext.getThreadLocal();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
