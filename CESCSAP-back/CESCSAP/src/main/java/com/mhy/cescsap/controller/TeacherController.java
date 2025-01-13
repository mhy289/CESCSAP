package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    // 教师相关操作
    // 获取单个教师
    @GetMapping("/teacher/{id}")
    public Result getOneTeacher(@PathVariable Integer id){
        return new Result(teacherService.getTeacherById(id));
    }
    // 获取多个教师
}
