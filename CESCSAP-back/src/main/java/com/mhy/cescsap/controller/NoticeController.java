package com.mhy.cescsap.controller;


import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
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
        if(id == null){
            return new Result(Result.fail(400, "参数不能为空"));
        }
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
        log.debug("id is {}",id);
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
        if(notice == null){
            throw new BusinessException(ExceptionType.PARAM_PASS_ERROR,"参数传递失败");
        }
        return new Result(noticeService.addNotice(notice));
    }

    //根据内容查询公告
    @GetMapping("/notices/content/{content}")
    public Result getNoticesByContent(@PathVariable String content){
        return new Result(noticeService.getNoticeByContent(content));
    }

    //根据标题查询公告
    @GetMapping("/notices/title/{title}")
    public Result getNoticesByTitle(@PathVariable String title){
        return new Result(noticeService.getNoticeByTitle(title));
    }

    //分页查询公告
    @GetMapping("/notices/page/{page}/size/{size}")
    public Result getNoticesByPage(@PathVariable Integer page, @PathVariable Integer size){
        return new Result(noticeService.getAllNotices(page, size));
        //return new Result(noticeService.getNoticeList());
    }

    //根据标题 分页查询公告
    @GetMapping("/notices/title/{title}/page/{page}/size/{size}")
    public Result getNoticesByTitlePage(@PathVariable String title, @PathVariable Integer page, @PathVariable Integer size){
        return new Result(noticeService.getNoticeByTitlePage(title, page, size));
    }

    //根据发布者查询公告
    @GetMapping("/notices/publisher/{publisher}")
    public Result getNoticesByPublisher(@PathVariable String publisher){
        return new Result(noticeService.getNoticeByPublisher(publisher));
    }

    //查询公告具体内容
//    @GetMapping("/notice/{id}")
//    public Result getNoticeContent(@PathVariable Long id){
//        return new Result(noticeService.getNotice(id));
//    }
}
