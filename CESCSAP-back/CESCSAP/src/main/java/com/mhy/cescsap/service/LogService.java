package com.mhy.cescsap.service;

import com.mhy.cescsap.mapper.LogMapper;
import com.mhy.cescsap.pojo.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LogService {
    //记录操作日志
    //查询一条日志
    Log getLogById(Integer logId);
    //查询所有日志
    List<Log> getAllLogs();
    //添加一条日志
    Integer addLog(Log log);
    //删除一条日志
    Integer deleteLog(Integer logId);
    //修改一条日志
    Integer updateLog(Log log);
}
