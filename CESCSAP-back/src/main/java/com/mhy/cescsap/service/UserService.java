package com.mhy.cescsap.service;


import com.mhy.cescsap.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    //登录
    User login(User user,String loginType);

    //检查身份
    Integer checkAdmin(Integer integer);

    User getUser(User user);
}
