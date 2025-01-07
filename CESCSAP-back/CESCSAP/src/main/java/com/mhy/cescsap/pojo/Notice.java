package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
    //公告ID
    private Long noticeId;
    //公告标题
    private String title;
    //公告内容
    private String content;
    //公告发布人
    private String publisher;
    //公告创建时间
    private String createTime;
}
