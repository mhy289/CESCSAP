package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.ClientErrorLogDTO;
import com.mhy.cescsap.pojo.Log;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    //前端异常日志传入
    @PostMapping("/log/clienterr")
    public Result receiveClientError(@RequestBody ClientErrorLogDTO dto, HttpServletRequest request) {
        Log log = new Log();
        log.setEventType("CLIENT ERROR");
        log.setEventContent(dto.getType() + ": " + dto.getMessage());
        log.setEventTime(new Date());
        log.setEventSource(dto.getUrl());
        log.setEventStatus("FAILED");
        // 从 token 中解析
        String token = request.getHeader("Authorization");
        log.setEventUser(token);
        log.setEventRemark(dto.getStack());

        // 可以把更多字段存到 remark 或扩展字段
        try {
            logService.addLog(log);
        } catch (Exception e) {
            // 如果记录日志失败，不要抛出，否则前端也会收到 500
            e.printStackTrace();
        }
        return new Result(log,"客户端异常已上报");
    }
}
