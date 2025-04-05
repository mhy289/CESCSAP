package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Log;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class LogController {

    @Autowired
    LogService logService;

    // 日志相关操作
    // 获取所有日志
    @GetMapping("/logs")
    public Result getAllLogs(){
        return new Result(logService.getAllLogs());
    }

    // 获取单个日志
    @GetMapping("/log/{id}")
    public Result getOneLog(@PathVariable Long id){
        return new Result(logService.getLogById(id));
    }

    // 删除单个日志
    @DeleteMapping("/log/{id}")
    public Result deleteLog(@PathVariable Long id){
        logService.deleteLog(id);
        return new Result("Delete log success");
    }

    // 修改单个日志
    @PutMapping("/log")
    public Result updateLog(@RequestBody Log log){
        logService.updateLog(log);
        return new Result("Update log success");
    }

    // 增加单个日志
    @PostMapping("/log")
    public Result addLog(@RequestBody Log log){
        logService.addLog(log);
        return new Result("Add log success");
    }

    //分页查询公告
    @GetMapping("/logs/page/{page}/size/{size}")
    public Result getNoticesByPage(@PathVariable Integer page, @PathVariable Integer size){
        return new Result(logService.getAllLogs(page, size));
        //return new Result(noticeService.getNoticeList());
    }
}
