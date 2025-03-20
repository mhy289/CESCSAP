package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    // 教师相关操作
    // 获取单个教师
    @GetMapping("/teacher/{id}")
    public Result getOneTeacher(@PathVariable Long id){
        return new Result(teacherService.getTeacherById(id));
    }

    // 获取多个教师
    @GetMapping("/teachers")
    public Result getAllTeachers(){
        return new Result(teacherService.getTeachers());
    }

    // 增加一个教师
    @PostMapping("/teacher")
    public Result addTeacher(Teacher teacher){
        return new Result(teacherService.addTeacher(teacher));
    }

    // 删除一个教师
    @DeleteMapping("/teacher/{id}")
    public Result deleteTeacher(@PathVariable Long id){
        return new Result(teacherService.deleteTeacher(id));
    }

    // 修改一个教师
    @PutMapping("/teacher")
    public Result updateTeacher(Teacher teacher){
        return new Result(teacherService.updateTeacher(teacher));
    }
}
