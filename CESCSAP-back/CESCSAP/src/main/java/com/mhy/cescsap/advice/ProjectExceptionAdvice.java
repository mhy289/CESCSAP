package com.mhy.cescsap.advice;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.StudentException;
import com.mhy.cescsap.pojo.Log;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import static com.mhy.cescsap.utils.JwtUtils.*;

@RestControllerAdvice  //用于标识当前类为REST风格对应的异常处理器
@Aspect
@Slf4j
public class ProjectExceptionAdvice {

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    //统一处理所有的Exception异常
    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex){
        createAndSaveLog(ex,"SYSTEM ERROR","系统异常");
        Result result = new Result(null,"系统错误",599);
        result.setData(ex.getMessage());
        return result;
    }

    //统一处理所有的学生异常
    @ExceptionHandler(BusinessException.class) 
    public Result doStudentException(BusinessException e){
        createAndSaveLog(e,"STUDENT ERROR","学生相关异常");
        Result result = new Result(null,"学生相关异常",520);
        result.setData(e.getMessage());
        return result;
    }

    //统一处理所有的教师异常
    @ExceptionHandler(BusinessException.class)
    public Result doTeacherException(BusinessException e){
        createAndSaveLog(e,"TEACHER ERROR","教师相关异常");
        Result result = new Result(null,"教师相关异常",521);
        result.setData(e.getMessage());
        return result;
    }

    //统一处理所有的管理员异常
    @ExceptionHandler(BusinessException.class)
    public Result doAdminException(BusinessException e){
        createAndSaveLog(e,"ADMIN ERROR","管理员相关异常");
        Result result = new Result(null,"管理员相关异常",522);
        result.setData(e.getMessage());
        return result;
    }

    // 统一处理所有的用户相关的Exception
    @ExceptionHandler(BusinessException.class)
    public Result doUserException(BusinessException e){
        Result r = new Result();
        String evenType = "UserException";
        log.debug("eventType");
        switch (e.getCode()){
            case USER_NOT_FOUND:
                r = Result.fail(500,"用户找不到");
                r.setData(e.getMessage());
                break;
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
        }
        log.debug("ddd");
        createAndSaveLog(e,evenType,e.getMessage());
        return r;
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException e){
        Result r = new Result();
        String evenType = "BusinessException";
        log.debug("eventType");
        switch (e.getCode()){
            case USER_NOT_FOUND:
                r = Result.fail(500,"用户找不到");
                r.setData(e.getMessage());
                break;
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
        log.debug("ddd");
        createAndSaveLog(e,evenType,e.getMessage());
        return r;
    }

    private void createAndSaveLog(Exception ex, String eventType, String eventContent) {
        Log log = new Log();
        log.setEventType(eventType);
        log.setEventContent(eventContent);
        log.setEventTime(new Date());
        log.setEventSource(request.getRequestURI());
        log.setEventStatus("FAILED");

        // 从安全上下文获取用户信息（根据你的认证系统调整）
        String token = request.getHeader("Authorization");
        System.out.println("-------------------------------------------------------");
        System.out.println(token);
        if(token==null || token.isEmpty()) {
            log.setEventUser("anonymous");
            //return;
        }
        else if(verifyToken(token)){
            String audience = getAudience(token);
            String username = getUserName(token);
            log.setEventUser(username);
        } else{
            log.setEventUser("anonymous");
        }

//        if (authentication != null) {
//            log.setEventUser(authentication.getName());
//        }

        // 添加堆栈跟踪信息
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // 截断堆栈跟踪，防止过长
        int maxLength = 1000; // 根据数据库定义调整这个值
        if (stackTrace.length() > maxLength) {
            stackTrace = stackTrace.substring(0, maxLength) + "...";
        }
        log.setEventRemark(stackTrace);

        System.out.println(log);
        try {
            logService.addLog(log);
        } catch (Exception logEx) {
            System.err.println("记录日志失败: " + logEx.getMessage());
        }
    }
}