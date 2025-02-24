package com.mhy.cescsap.pojo;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {
    // 层id
    private Long floorId;
    // 身份标识
    private Long role;
    // 用户
    private User user;
    //发言时间
    private Date replyTime;
    //发言距离现在多少时间
    private Date replyTimeDistance;
    //内容
    private String content;
    //回复对象
    private User replyToUser;
    //楼中楼
    private Floor floor;
}
