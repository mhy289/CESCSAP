package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//帖子
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partition {
    // 贴子id
    private Long partitionId;
    // 帖子标题
    private String partitionTitle;
    // 帖子内容
    private String partitionContent;
    //发帖时间
    private Date partitionTime;
}
