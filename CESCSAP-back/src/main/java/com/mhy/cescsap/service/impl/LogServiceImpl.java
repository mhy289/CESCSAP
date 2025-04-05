package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.LogMapper;
import com.mhy.cescsap.pojo.Log;
import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public Log getLogById(Long logId) {
        return logMapper.getLogById(logId);
    }

    @Override
    public List<Log> getAllLogs() {
        return logMapper.getAllLogs();
    }

    @Override
    public Integer addLog(Log log) {
        return logMapper.addLog(log);
    }

    @Override
    public Integer deleteLog(Long logId) {
        return logMapper.deleteLog(logId);
    }

    @Override
    public Integer updateLog(Log log) {
        return logMapper.updateLog(log);
    }

    @Override
    public PageItem<Log> getAllLogs(Integer page, Integer size) {
        if(page == null || size==null){
            throw new RuntimeException();
        }
        log.debug("logs is {}", logMapper.getAllLogs());
        //分页查询公告
        PageHelper.startPage(page, size);
        List<Log> logs = logMapper.getAllLogs();
        log.debug("logs is {}", logs);
        Page<Log> pages = (Page<Log>) logs;
        return new PageItem<>(pages.getTotal(), logs);
    }
}
