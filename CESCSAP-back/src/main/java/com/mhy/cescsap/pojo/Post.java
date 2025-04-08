package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//帖子
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    // 贴子id
    private Long postId;
    // 分区id
    private Long partitionId;
    // 贴子标题
    private String postTitle;
    // 贴子内容
    private String postContent;
    // 贴子作者
    private String postAuthor;
    // 贴子创建时间
    private String createTime;
    // 贴子浏览量
    private Long viewCount;
    // 贴子回复量
    private Long replyCount;
}
