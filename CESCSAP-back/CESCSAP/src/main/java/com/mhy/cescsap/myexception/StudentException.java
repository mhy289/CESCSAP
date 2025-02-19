package com.mhy.cescsap.myexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentException extends RuntimeException {
    ExceptionType code;

    public StudentException(ExceptionType code, String message){
        super(message);
        this.code = code;
    }

    public StudentException(ExceptionType code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }
}
