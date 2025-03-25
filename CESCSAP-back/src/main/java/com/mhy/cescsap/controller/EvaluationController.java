package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.CourseService;
import com.mhy.cescsap.service.EvaluationService;
import com.mhy.cescsap.service.SCService;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@Slf4j
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    StudentService studentService;

    @Autowired
    SCService scService;

    @Autowired
    CourseService courseService;

    //获取教师评价
    @GetMapping("/eval")
    public Result getEvaluationByTeacher_old(@RequestBody Teacher teacher){
        Long teacherId = teacher.getTeacherId();
        List<EvaluationCriterion> evaluations = evaluationService.getEvaluationsByTeacherId(teacherId);
        return new Result(evaluations);
    }

    @PostMapping("/evals")
    public Result getEvaluationByTeacher(@RequestBody Student student){
        if(student.getName()==null){
            throw  new BusinessException(ExceptionType.STUDENT_NAME_ERROR,"学生名字传输错误");
        }
        log.debug("-------------------------------");
        log.debug("getEvaluationByTeacher : " + student.getName());
        String name = student.getName();
        student = studentService.getStudentByName(name);
        Long studentId = student.getStudentId();
        List<StudentCourse> studentCourses = scService.getAllSCByStudentId(studentId);
        List<List<EvaluationCriterion>> evaluationss = new ArrayList<>();
        for(StudentCourse studentCourse : studentCourses) {
            Long courseId = studentCourse.getCourseId();
            Course course = courseService.getCourseById(courseId);
            Long teacherId = course.getTeacherId();
            List<EvaluationCriterion> evaluations = evaluationService.getEvaluationsByTeacherId(teacherId);
            evaluationss.add(evaluations);
        }
        return new Result(evaluationss);
    }

    //评价教师
    @PostMapping("/eval")
    public Result evaluateTeacher(@RequestBody Double[] scores, @RequestBody EvaluationCriterion evaluationCriterion, @RequestBody String name){
        for(Double s : scores){
            Integer i = evaluationService.updateEvaluationByTeacher(evaluationCriterion, s);
            if(i==0){
                throw new RuntimeException();
            }
        }
        Student student = studentService.getStudentByName(name);
        student.setEvaluateStatus(0); // 标记为已评论
        return new Result(studentService.updateCommentStatus(student),"success");
    }
}
