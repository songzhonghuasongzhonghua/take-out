package com.song.controller.admin;

import com.song.dto.SetmealDTO;
import com.song.result.Result;
import com.song.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 添加套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody SetmealDTO setmealDTO) {
        log.info("插入套餐：{}", setmealDTO);
        setmealService.insertWithDish(setmealDTO);
        return Result.success();
    }
}
