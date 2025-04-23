package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PostService {
    //查看分区下所有帖子
    List<Post> findPostByPartitionId(Long partitionId);

    //发帖
    Integer addPost(Post p);

    //获取帖子
    Post getPost(Long postId);

    //增加浏览量
    Integer incrementViewCount(Long postId);

    //增加回复量
    Integer incrementReplyCount(Long postId);

    //删除帖子
    Integer deletePost(Long postId);
}
