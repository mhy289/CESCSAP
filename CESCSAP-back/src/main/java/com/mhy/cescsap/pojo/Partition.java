package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//分区
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partition {
    // 分区id
    private Long partitionId;
    // 分区标题
    private String partitionTitle;
    // 分区内容
    private String partitionContent;
    //分区时间
    private Date partitionTime;
}
