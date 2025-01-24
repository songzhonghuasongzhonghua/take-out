package com.song.controller.user;

import com.song.dto.ShoppingCartDTO;
import com.song.entity.ShoppingCart;
import com.song.result.Result;
import com.song.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingcart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车:{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }


    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShoppingCart() {
        log.info("查看购物车");
        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCart();
        return Result.success(shoppingCartList);
    }

    @DeleteMapping("/clean")
    public Result clean(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }
}
