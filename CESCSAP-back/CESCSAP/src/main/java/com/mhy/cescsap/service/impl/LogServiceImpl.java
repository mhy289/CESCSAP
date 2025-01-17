package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.LogMapper;
import com.mhy.cescsap.pojo.Log;
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
    public Log getLogById(Integer logId) {
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
    public Integer deleteLog(Integer logId) {
        return logMapper.deleteLog(logId);
    }

    @Override
    public Integer updateLog(Log log) {
        return logMapper.updateLog(log);
    }
}
