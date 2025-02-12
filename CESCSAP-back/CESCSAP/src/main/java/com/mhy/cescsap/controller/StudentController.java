package com.mhy.cescsap.controller;

import com.mhy.cescsap.advice.ProjectExceptionAdvice;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.StudentCourse;
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

    //分页获取
    @GetMapping("/students/current/{current}/size/{size}")
    public Result getAllPage(@PathVariable Integer current, @PathVariable Integer size) {
        return new Result(scService.queryPage(current, size), "查询成功", 200);
    }

    // 条件分页查询
    @PostMapping("/students/current/{current}/size/{size}")
    public Result getConditionPage(@PathVariable Integer current, @PathVariable Integer size, @RequestBody Student student) {
        if(student.getName() == null){
            throw new BusinessException(ExceptionType.STUDENT_ID_NOT_FOUND,"没有对象");
        }
        Student student2 = studentService.getStudentByName(student.getName());
        log.debug("AAAAAAAAAAAAAAAAAAAAAAStudent is {}",student2);
        return new Result(scService.queryConditionPage(student2, current, size), "查询成功", 200);
    }

    //学生添加课程
    @PostMapping("/student/addCourse")
    public Result addStudentCourse(@RequestBody StudentCourse sc){
        return new Result(scService.addSC(sc));
    }
}
