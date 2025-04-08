package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Partition;
import com.mhy.cescsap.pojo.Post;
import com.mhy.cescsap.pojo.Reply;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.PartitionService;
import com.mhy.cescsap.service.PostService;
import com.mhy.cescsap.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/forum")
@Slf4j
public class ForumController {

    @Autowired
    PartitionService partitionService;

    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;

    // 管理员：新建分区
    @PostMapping("/partitions")
    public Result createPartition(@RequestBody Partition p) {
        p.setPartitionTime(new Date());
        Integer i = partitionService.addPartition(p);
        return new Result(i);
    }

    // 用户：查看所有分区
    @GetMapping("/partitions")
    public Result listPartitions() {
        List<Partition> allPartitions = partitionService.getAllPartitions();
        log.debug("allPartitions:{}", allPartitions);
        return new Result(allPartitions);
    }

    // 查看分区下帖子
    @GetMapping("/partitions/{pid}/posts")
    public Result listPosts(@PathVariable Long pid) {
        return new Result(postService.findPostByPartitionId(pid));
    }

    // 发帖（正文即 1L）
    @PostMapping("/partitions/{pid}/posts")
    public Result createPost(@PathVariable Long pid, @RequestBody Post p) {
        p.setPartitionId(pid);
        p.setCreateTime(LocalDateTime.now().toString());
        p.setViewCount(0L);
        p.setReplyCount(0L);
        Integer i = postService.addPost(p);
        return new Result(i);
    }

    // 获取单个帖子详情（含正文当作 1L）
    @GetMapping("/posts/{postId}")
    public Result getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        // 增加浏览量
        Integer i = postService.incrementViewCount(postId);
        return new Result(post);
    }

    // 帖子下回复列表（从 2L 开始）
    @GetMapping("/posts/{postId}/replies")
    public Result listReplies(@PathVariable Long postId) {
        List<Reply> list = replyService.listByPost(postId);
        return new Result(list);
    }

    // 回复帖子
    @PostMapping("/posts/{postId}/replies")
    public Result createReply(@PathVariable Long postId, @RequestBody Reply r) {
        r.setPostId(postId);
        r.setReplyTime(new Date());
        Integer i = replyService.addReply(r);
        // 增加回复数
        Integer s = postService.incrementReplyCount(postId);
        return new Result(r);
    }
}
