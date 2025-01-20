package com.mhy.cescsap.advice;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //用于标识当前类为REST风格对应的异常处理器
public class ProjectExceptionAdvice {

    //统一处理所有的Exception异常
    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex){
        Result result = new Result(null,"系统错误",500);
        result.setData(ex.getMessage());
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException e){
        Result r = new Result();
        switch (e.getCode()){
            case BUS_ID_NOT_FOUND:
                r = Result.fail(501,"ID找不到");
                r.setData(e.getMessage());
                break;
            case BUS_INTEGER_ABC:
                r = Result.fail(502,"参数ABC不符合要求");
                r.setData(e.getMessage());
                break;
            case BUS_INVALID_TOKEN:
                r = Result.fail(444,"Token不合法");
                r.setData(e.getMessage());
                break;
            case DISH_NOT_FOUND:
                r = Result.fail(503,"菜品找不到");
                r.setData(e.getMessage());
                break;
            default:
                r.setMsg(e.getMessage());
                r.setCode(505);
                break;
        }
        return r;
    }
}