package com.song.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class Mytask {


//    @Scheduled(cron = "0/5 * * * * ? ")
//    public void task(){
//        log.info("定时任务开启: {}", LocalDateTime.now());
//    }
}
