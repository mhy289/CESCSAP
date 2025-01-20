package com.mhy.cescsap.myexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException{
    ExceptionType code;

    public SystemException(ExceptionType code, String message){
        super(message);
        this.code = code;
    }

    public SystemException(ExceptionType code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }
}
