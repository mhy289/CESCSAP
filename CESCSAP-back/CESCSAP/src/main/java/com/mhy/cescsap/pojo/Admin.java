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
    //密码
    private String password;
    // 联系方式
    private String contact;
    //登陆状态
    private boolean loginStatus;
}
