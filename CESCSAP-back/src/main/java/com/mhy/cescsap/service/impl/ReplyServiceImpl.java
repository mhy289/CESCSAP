package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.ReplyMapper;
import com.mhy.cescsap.mapper.SectionMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
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

    @Autowired
    SectionMapper sectionMapper;

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
        try {
            Integer i = replyMapper.addReply(reply);
        } catch (Exception e){
            throw new BusinessException(ExceptionType.OTHER, "添加失败");
        }
        return 1;
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
        return sectionMapper.getAllSections();
    }

    @Override
    public List<Reply> listByPost(Long postId) {
        return replyMapper.getReplyContent(new Reply(postId));
    }
}
