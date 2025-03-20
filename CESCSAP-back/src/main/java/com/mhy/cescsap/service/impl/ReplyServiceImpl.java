package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.ReplyMapper;
import com.mhy.cescsap.pojo.Partition;
import com.mhy.cescsap.pojo.Reply;
import com.mhy.cescsap.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    @Override
    public List<Reply> getReplyCount() {
        return replyMapper.getReplyList();
    }

    @Override
    public List<Reply> getReplyByUser(Reply reply) {
        return replyMapper.getReplyContent(reply);
    }

    @Override
    public Integer getReplyById(Long id) {
        return null;
    }

    @Override
    public Integer updateReply(Long id) {
        return null;
    }

    @Override
    public Integer addReply(Reply reply) {
        return replyMapper.addReply(reply);
    }

    @Override
    public Integer deleteReply(Long id) {
        return null;
    }

    @Override
    public Integer deleteReplyByUser(Reply reply) {
        return replyMapper.deleteReplyByUser(reply);
    }

    @Override
    public Integer updateReplyByUser(Reply reply) {
        return replyMapper.updateReplyByUser(reply);
    }

    @Override
    public List<Partition> getSections() {
        return null;
    }
}
