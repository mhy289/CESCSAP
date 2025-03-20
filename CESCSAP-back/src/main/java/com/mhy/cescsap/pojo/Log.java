package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    // 日志id
    private Long logId;
    // 事件类型
    private String eventType;
    // 事件内容
    private String eventContent;
    // 事件发生时间
    private Date eventTime;
    // 事件发生者
    private String eventUser;
    // 事件来源
    private String eventSource;
    // 事件备注
    private String eventRemark;
    // 事件状态
    private String eventStatus;
}
