package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Partition;
import com.mhy.cescsap.pojo.Reply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReplyService {
    //查询所有回复
    List<Reply> getReplyCount();

    //查询该分区帖子所有回复
    List<Reply> getReplyByUser(Reply reply);

    //查询单个回复
    Integer getReplyById(Long id);

    //修改单个回复
    Integer updateReply(Long id);

    //添加回复
    Integer addReply(Reply reply);

    //删除回复
    Integer deleteReply(Long id);

    Integer deleteReplyByUser(Reply reply);

    Integer updateReplyByUser(Reply reply);

    List<Partition> getSections();

    List<Reply> listByPost(Long postId);
}
