package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Reply;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mhy.cescsap.utils.IpUtils.getClientIpAddress;

@RestController
@CrossOrigin
@Slf4j
public class ReplyController {

    @Autowired
    ReplyService replyService;

    // 发送留言
    @PostMapping("/reply")
    public Result sendReply(Reply reply) {
        Result r = new Result();
        Integer i = replyService.addReply(reply);
        //通过工具类获取发言者ip
        //getClientIpAddress(reply);
        if (i > 0) {
            r.setMsg("留言成功");
            r.setCode(200);
        } else{
            r.setMsg("留言失败");
            r.setCode(500);
        }
        r.setData(i);
        return r;
    }
    // 获取留言
    @PostMapping("/replies")
    public Result getReply(Reply reply){
        return new Result(replyService.getReplyByUser(reply));
    }
    // 删除留言
    @PostMapping("/del-reply")
    public Result deleteReply(Reply reply){
        return new Result(replyService.deleteReplyByUser(reply));
    }
    // 编辑留言
    @PostMapping("/re-reply")
    public Result updateReply(Reply reply){
        return new Result(replyService.updateReplyByUser(reply));
    }

    //获取分区
    @GetMapping("/sections")
    public Result getReplyByPage(){
        return new Result(replyService.getSections());
    }

    //获取某一分区的留言
}
