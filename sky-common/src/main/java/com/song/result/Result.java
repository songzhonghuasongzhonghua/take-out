package com.song.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;


    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static Result success(){
        return new Result(0,"操作成功",null);
    }


    public static Result error(String message){
        return new Result(0,message,null);
    }

    public static <T> Result<T>  success(T data){
        return new Result(0,"操作成功",data);
    }
}
