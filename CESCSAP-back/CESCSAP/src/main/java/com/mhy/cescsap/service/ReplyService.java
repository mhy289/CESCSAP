package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Reply;

import java.util.List;

public interface ReplyService {
    //查询所有回复
    List<Reply> getReplyCount();

    //查询单个回复
    Integer getReplyById(Long id);

    //修改单个回复
    Integer updateReply(Long id);

    //添加回复
    Integer addReply(Reply reply);

    //删除回复
    Integer deleteReply(Long id);
}
