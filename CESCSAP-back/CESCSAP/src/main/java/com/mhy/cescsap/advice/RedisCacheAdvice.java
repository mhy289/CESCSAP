package com.mhy.cescsap.advice;

import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.myexception.SystemException;
import com.mhy.cescsap.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class RedisCacheAdvice {
/*
    @Autowired
    RedisTemplate redisTemplate;

    // 定义切入点

    @Pointcut("execution(* com.mhy.cescsap.mapper.*Mapper.select*(..))")
    public void ptSelectById(){

    }

    @Pointcut("execution(* com.mhy.cescsap.mapper.*Mapper.update*(..))")
    public void ptUpdate(){

    }

    @Pointcut("execution(* com.mhy.cescsap.mapper.*Mapper.delete*(..))")
    public void ptDelete(){

    }

    @Around("ptSelectById()")
    public Object handleSelectCache(ProceedingJoinPoint pjp) throws Throwable{
        HashOperations ops = redisTemplate.opsForHash();
        log.debug("\n Execution");
        Object[] args = pjp.getArgs();
        Integer id = (Integer) args[0];
        log.debug("target class is {}", pjp.getTarget().getClass().getInterfaces()[0]);
        String tablename = getTableName(pjp);
        log.debug("id is {}, tablename is {}",id,tablename);
        String resJson = (String) ops.get(tablename, id.toString());
        log.debug("redis json is {}",resJson);
        //读redis
        if(resJson != null && !resJson.isEmpty()){
            Class<?> mapperClazz = pjp.getTarget().getClass().getInterfaces()[0];
            Class<?> returnClazz = getReturnType(mapperClazz);
            Object cache = JsonUtils.jsonToPojo(resJson, returnClazz);
            log.debug("return redis json is {}", cache);
            log.debug("\n redis数据");
            return cache;
        }
        Object result = pjp.proceed(args);
        //写redis
        if(result!=null){
//            String json = ops.get(tablename, id.toString()).toString();
            String json = JsonUtils.objectToJson(result);
            ops.put(tablename, id.toString(), json);
            log.debug("\n mysql数据");
        }
        return result;
    }

    private String getTableName(ProceedingJoinPoint pjp) {
        Class<?> mapperClazz = pjp.getTarget().getClass().getInterfaces()[0];
        String mapperName = mapperClazz.getName();
        String tablename = mapperName.substring(mapperName.lastIndexOf(".") + 1);
        return tablename;
    }

    @Around("ptUpdate()")
    public Object handleUpdateCache(ProceedingJoinPoint pjp) throws Throwable{
        HashOperations ops = redisTemplate.opsForHash();
        Object[] args = pjp.getArgs();
        //得到表名称
        String tableName = getTableName(pjp);
        //得到id
        Object pojo = args[0];
        //得到类对象
        Class<?> pojoClazz = pojo.getClass();
        //得到属性对象
        Field idField = pojoClazz.getDeclaredField("id");
        idField.setAccessible(true);
        Integer id = (Integer) idField.get(pojo);
        log.debug("tableName is {} id is {}",tableName, id);
        ops.delete(tableName, id.toString());

        Object ret = pjp.proceed(args);
        return ret;
    }

    @Around("ptDelete()")
    public Object handleDeleteCache(ProceedingJoinPoint pjp) throws Throwable{
        HashOperations ops = redisTemplate.opsForHash();
        Object[] args = pjp.getArgs();
        //得到表名称
        String tableName = getTableName(pjp);
        //得到id
        Integer id = (Integer) args[0];
        log.debug("tableName is {} id is {}",tableName, id);
        ops.delete(tableName, id.toString());

        Object ret = pjp.proceed(args);
        return ret;
    }


    /**
     *
     * @param mapperClazz
     * @return
     */
    /*private Class<?> getReturnType(Class<?> mapperClazz) {
        try {
            Method selectById = mapperClazz.getDeclaredMethod("selectById", Integer.class);
            Class<?> returnType = selectById.getReturnType();
            return returnType;
        } catch (Exception e){
            throw new SystemException(ExceptionType.SYS_METHOD_MISSED, "方法不能找到",e);
        }
    }*/
}
