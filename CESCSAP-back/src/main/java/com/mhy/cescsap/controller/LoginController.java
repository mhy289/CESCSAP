package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.User;
import com.mhy.cescsap.service.StudentService;
import com.mhy.cescsap.service.TeacherService;
import com.mhy.cescsap.service.UserService;
import com.mhy.cescsap.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.LongValue;
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
    public Result login(@RequestBody User user, @RequestParam String loginType, HttpServletRequest request) {
        log.debug("user is {}", user);
        Result result = new Result();
        User addDb = userService.login(user,loginType);
        log.debug("user0");
        if (addDb == null) {
            log.debug("user1");
            //throw new BusinessException(ExceptionType.USER_NOT_FOUND,"找不到用户");
            result.setCode(500);
            result.setMsg("登陆失败");
            return result;
        }
        log.debug("user2");
        String token = JwtUtils.createToken(addDb.getId().toString(), addDb.getName());
        result.setData(token);
        result.setCode(200);
        result.setMsg(user.getName());
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

    @GetMapping("/person")
    public Result getUser(@RequestParam String name, @RequestParam Long role) {
        if(name==null || role==null){
            throw new BusinessException(ExceptionType.USER_NOT_FOUND,"用户未传入");
        }
        User user=new User();
        user.setName(name);
        user.setRole(role);
        User user1 = userService.getUser(user);
        log.debug("user is {}",user1);
        return new Result(user1);
    }

    //通过id获取用户名
    @GetMapping("/username/{id}")
    public Result getUserName(@RequestParam Long id) {
        if(id==null){
            throw new BusinessException(ExceptionType.USER_NOT_FOUND,"用户未传入");
        }
        User user=new User();
        user.setId(id);
        User user1 = userService.getUser(user);
        log.debug("user is {}",user1);
        return new Result(user1.getName());
    }

    //通过账号获取用户名
    @GetMapping("/name/{account}")
    public Result getUserNameByAccount(@RequestParam String account) {
        if(account==null){
            throw new BusinessException(ExceptionType.USER_NOT_FOUND,"用户未传入");
        }
        User user=new User();
        User user1 = userService.getUser(user);
        log.debug("user is {}",user1);
        return new Result(user1.getName());
    }
}
