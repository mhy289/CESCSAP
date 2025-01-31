package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.service.SCService;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    SCService scService;
    //查询所有学生
    @GetMapping("/students")
    public Result getAllStudents(){
        return new Result(studentService.getStudents());
    }

    //查询一个学生
    @GetMapping("/student/{id}")
    public Result getOneStudent(@PathVariable Long id){
        return new Result(studentService.getStudentById(id));
    }

    //增加一个学生
    @PostMapping("/student")
    public Result addStudent(@RequestBody Student student){
        return new Result(studentService.addStudent(student));
    }

    // 删除一个学生
    @DeleteMapping("/student/{id}")
    public Result deleteStudent(@PathVariable Long id){
        return new Result(studentService.deleteStudent(id));
    }

    // 更新一个学生
    @PutMapping("/student")
    public Result updateStudent(@RequestBody Student student){
        return new Result(studentService.updateStudent(student));
    }

    // 查看个人成绩
    @GetMapping("/studentGrade/{id}")
    public Result getStudentGrade(@PathVariable Long id){
        return new Result(scService.getSC(id));
    }
}
