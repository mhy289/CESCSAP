package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.PostMapper;
import com.mhy.cescsap.mapper.SectionMapper;
import com.mhy.cescsap.pojo.Post;
import com.mhy.cescsap.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;

    @Override
    public List<Post> findPostByPartitionId(Long partitionId) {
        return postMapper.findPostByPartitionId(partitionId);
    }

    @Override
    public Integer addPost(Post p) {
        return postMapper.addPost(p);
    }

    @Override
    public Post getPost(Long postId) {
        return postMapper.getPost(postId);
    }

    @Override
    public Integer incrementViewCount(Long postId) {
        return postMapper.incrementViewCount(postId);
    }

    @Override
    public Integer incrementReplyCount(Long postId) {
        return postMapper.incrementReplyCount(postId);
    }

    @Override
    public Integer deletePost(Long postId) {
        return postMapper.deletePost(postId);
    }
}
