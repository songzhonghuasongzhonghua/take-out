package com.song.controller.admin;

import com.song.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    private String STATUS_KEY = "status_key";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态: {}",status == 1 ? "营业中":"休息");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(STATUS_KEY,status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        log.info("获取店铺状态");
        Integer status = (Integer) redisTemplate.opsForValue().get(STATUS_KEY);
        return Result.success(status);
    }
}
