package com.mhy.cescsap.controller;

import com.mhy.cescsap.advice.ProjectExceptionAdvice;
import com.mhy.cescsap.mapper.StudentMapper;
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
    public Result getAllStudents() {
        return new Result(studentService.getStudents());
    }

    //查询一个学生
    @GetMapping("/student/{id}")
    public Result getOneStudent(@PathVariable Long id) {
        return new Result(studentService.getStudentById(id));
    }

    //增加一个学生
    @PostMapping("/student")
    public Result addStudent(@RequestBody Student student) {
        Integer i = studentService.addStudent(student);
        if (i > 0) {
            //为学生添加学号
            student.setStudentId(studentService.getStudentByName(student.getName()).getStudentId());
            Integer j = studentService.addStudentNumber(student);
            return new Result(j);
        } else {
            throw new BusinessException(ExceptionType.STUDENT_ID_NOT_FOUND, "添加失败");
        }

    }

    // 删除一个学生
    @DeleteMapping("/student/{id}")
    public Result deleteStudent(@PathVariable Long id) {
        return new Result(studentService.deleteStudent(id));
    }

    // 更新一个学生
    @PutMapping("/student")
    public Result updateStudent(@RequestBody Student student) {
        return new Result(studentService.updateStudent(student));
    }

    // 查看个人成绩
    @GetMapping("/studentGrade/{id}")
    public Result getStudentGrade(@PathVariable Long id) {
        return new Result(scService.getSC(id));
    }

    //分页获取
    @GetMapping("/students/page/{current}/size/{size}")
    public Result getAllPage(@PathVariable Integer current, @PathVariable Integer size) {
        return new Result(studentService.queryPage(current, size));
        //return new Result(scService.queryPage(current, size), "查询成功", 200);
    }

    // 条件分页查询
    @PostMapping("/students/current/{current}/size/{size}")
    public Result getConditionPage(@PathVariable Integer current, @PathVariable Integer size, @RequestBody Student student) {
        if (student.getName() == null) {
            throw new BusinessException(ExceptionType.STUDENT_ID_NOT_FOUND, "没有对象");
        }
        Student student2 = studentService.getStudentByName(student.getName());
        log.debug("AAAAAAAAAAAAAAAAAAAAAAStudent is {}", student2);
        return new Result(scService.queryConditionPage(student2, current, size), "查询成功", 200);
    }

    //学生添加课程
    @PostMapping("/student/addCourse")
    public Result addStudentCourse(@RequestBody StudentCourse sc) {
        return new Result(scService.addSC(sc));
    }

    //按条件筛选课程
    @PostMapping("/student/queryCourse")
    public Result queryStudentCourse(@RequestBody Student student) {
        return new Result(scService.queryStudentCourse(student));
    }

    // 获取筛选选项
    @PostMapping("/courses/filter-options")
    public Result getFilterOptions(@RequestBody Student student) {
        return new Result(studentService.getFilterOptions(student));
    }

    //刷新绩点
    @PostMapping("/student/refreshGPA")
    public Result refreshGPA(@RequestBody Student student) {
        return new Result(scService.refreshGPA(student));
    }

    //修改评价状态
    @PutMapping("/student/updateCommentStatus")
    public Result updateCommentStatus() {
        return new Result(studentService.updateCommentStatus());
    }

    //所有学生专业合法化检测
    @GetMapping("/student/major")
    public Result updateMajorStatus() {
        return new Result(studentService.updateMajor());
    }

    //计算所有学生的平均绩点
    @GetMapping("/students/averageGPA")
    public Result averageGPA() {
        Integer allSC = scService.getAllSC();
        if (allSC > 0) {
            return new Result(allSC);
        } else {
            throw new BusinessException(ExceptionType.GPA_ERR, "计算失败");
        }
    }

    //查询学生所有课程成绩
    @GetMapping("/student/{studentId}/scores")
    public Result getAllSCByStudentId(@PathVariable Long studentId) {
        return new Result(scService.getAllSCByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}/scoress")
    public Result getAllSCByStudentId2(@PathVariable Long studentId, @RequestParam(required = false) String term,  // 学期过滤（可选）
                                       @RequestParam(defaultValue = "1") Integer pageNum,  // 当前页（1-based）
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return new Result(scService.getAllSCByStudentId2(studentId, term, pageNum, pageSize));
    }
}
