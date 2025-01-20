package com.mhy.cescsap.myexception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    ExceptionType code;

    public BusinessException(ExceptionType code, String message){
        super(message);
        this.code = code;
    }

    public BusinessException(ExceptionType code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }

}
