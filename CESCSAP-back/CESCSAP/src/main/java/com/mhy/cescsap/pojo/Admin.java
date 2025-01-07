package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    // 管理员id
    private Long adminId;
    // 管理员名称
    private String name;
    // 联系方式
    private String contact;
    // 系统设置
    private String systemSettings;
    // 导出日志
    private String exportLogs;
    //备份状态
    private String backupStatus;
    //用户反馈日志列表
    private List<Feedback> feedbacks;
    //系统日志列表
    private List<SystemLog> systemLogs;
    //维护任务列表
    private List<MaintainTask> maintainTasks;
    //通知与公告列表
    private List<Notice> notices;
    //登陆状态
    private boolean loginStatus;
}
