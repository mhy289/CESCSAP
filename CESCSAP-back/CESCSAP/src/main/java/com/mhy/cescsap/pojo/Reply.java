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
    //用户id
    private Long userId;
    //内容
    private String content;
    //发言时间
    private Date replyTime;
}
