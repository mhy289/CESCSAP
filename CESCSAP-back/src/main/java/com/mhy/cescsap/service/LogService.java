package com.mhy.cescsap.service;

import com.mhy.cescsap.mapper.LogMapper;
import com.mhy.cescsap.pojo.Log;
import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.pojo.PageItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface LogService {
    //记录操作日志
    //查询一条日志
    Log getLogById(Long logId);
    //查询所有日志
    List<Log> getAllLogs();
    //添加一条日志
    Integer addLog(Log log);
    //删除一条日志
    Integer deleteLog(Long logId);
    //修改一条日志
    Integer updateLog(Log log);



    PageItem<Log> getAllLogs(Integer page, Integer size);
}
