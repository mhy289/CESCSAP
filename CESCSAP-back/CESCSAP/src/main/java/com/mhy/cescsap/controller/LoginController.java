package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.User;
import com.mhy.cescsap.service.StudentService;
import com.mhy.cescsap.service.TeacherService;
import com.mhy.cescsap.service.UserService;
import com.mhy.cescsap.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mhy.cescsap.utils.JwtUtils.getAudience;

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

    //登录
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

    //获取身份
    @GetMapping("/role")
    public Result RoleCheck(HttpServletRequest req){
        log.debug("role");
        String token = req.getHeader("Authorization");
        String audience = getAudience(token);
        Integer isAdmin = userService.checkAdmin(Integer.valueOf(audience));
        System.out.println(isAdmin);
        return new Result(isAdmin);
    }
}
