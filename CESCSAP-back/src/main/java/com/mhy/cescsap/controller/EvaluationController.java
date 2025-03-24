package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.EvaluationCriterion;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.EvaluationService;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@Slf4j
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    StudentService studentService;

    //获取教师评价
    @GetMapping("/eval")
    public Result getEvaluationByTeacher(@RequestBody Teacher teacher){
        Long teacherId = teacher.getTeacherId();
        List<EvaluationCriterion> evaluations = evaluationService.getEvaluationsByTeacherId(teacherId);
        return new Result(evaluations);
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
