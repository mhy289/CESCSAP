package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    //账号
    private Long account;
    private String name;
    private String password;
    private String number;
    //身份标识
    //管理员0 教师1 学生2
    private Long role;
}
