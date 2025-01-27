package com.mhy.cescsap.service;


import com.mhy.cescsap.pojo.User;

public interface UserService {

    //登录
    User login(User user);

    //检查身份
    Integer checkAdmin(Integer integer);
}
