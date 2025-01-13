package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Object data;
    private String msg;
    private Integer code;


    public Result(Object data){
        this.data = data;
        this.code = 200;
        this.msg = "success";
    }


    public Result(Object data, String message){
        this.data = data;
        this.code = 200;
        this.msg= message;
    }

    public static Result fail(Integer code,String msg){
        return new Result(null,msg,code);
    }
}
