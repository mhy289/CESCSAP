package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.StudentService;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class AdminController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @GetMapping("/admin/check")
    public Result check(){
        Integer t = teacherService.check();
        Integer s = studentService.check();
        if(s+t==0){
            return new Result(s+t,"数据异常",201);
        } else if (s+t==1) {
            return new Result(s+t,"数据异常",202);
        } else {
            return new Result(s+t,"数据正常",200);
        }
    }
}
