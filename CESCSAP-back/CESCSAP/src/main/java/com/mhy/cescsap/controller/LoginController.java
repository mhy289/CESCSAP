package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.User;
import com.mhy.cescsap.service.StudentService;
import com.mhy.cescsap.service.TeacherService;
import com.mhy.cescsap.service.UserService;
import com.mhy.cescsap.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class LoginController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    //教师登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.debug("user is {}", user);
        Result result = new Result();
        User addDb = userService.login(user);
        if (addDb == null) {
            result.setCode(500);
            result.setMsg("登陆失败");
            return result;
        }
        String token = JwtUtils.createToken(addDb.getId().toString(), addDb.getName());
        result.setData(token);
        result.setCode(200);
        result.setMsg("登陆成功");
        return result;
    }
}
