package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    //评论id
    private Long replyId;
    //帖子id
    private Long postId;
    //用户名
    private String userName;
    //内容
    private String content;
    //发言时间
    private Date replyTime;

    public Reply(Long postId) {
        this.postId = postId;
    }
}
