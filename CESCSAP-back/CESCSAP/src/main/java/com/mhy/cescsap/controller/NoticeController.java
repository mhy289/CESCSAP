package com.mhy.cescsap.controller;


import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class NoticeController {

    @Autowired
    NoticeService noticeService;
    // 公告相关操作
    // 获取单个公告
    @GetMapping("/notice/{id}")
    public Result getNotice(@PathVariable Long id){
        return new Result(noticeService.getNotice(id));
    }

    // 获取所有公告
    @GetMapping("/notices")
    public Result getAllNotices(){
        return new Result(noticeService.getNoticeList());
    }

    // 删除公告
    @DeleteMapping("/notice/{id}")
    public Result deleteNotice(@PathVariable Long id){
        return new Result(noticeService.deleteNotice(id));
    }

    // 编辑公告
    @PutMapping("/notice")
    public Result updateNotice(@RequestBody Notice notice){
        return new Result(noticeService.updateNotice(notice));
    }

    // 新增公告
    @PostMapping("/notice")
    public Result addNotice(@RequestBody Notice notice){
        return new Result(noticeService.addNotice(notice));
    }

    //根据内容查询公告
    @GetMapping("/notices/content/{content}")
    public Result getNoticesByContent(@PathVariable String content){
        return new Result(noticeService.getNoticeByContent(content));
    }
}
