package com.song.handler;

import com.song.constant.MessageConstant;
import com.song.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理sql异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result handleException(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'songzhonghua' for key 'employee.idx_username'
        String errorMessage = ex.getMessage();
        log.warn(errorMessage);
        if(errorMessage.contains("Duplicate entry")){
            String[] split = errorMessage.split(" ");
            String name = split[2];
            String msg = name + MessageConstant.ALEADY_EXIST;
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    @ExceptionHandler
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }



}
