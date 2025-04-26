package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.*;
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

    @Autowired
    TeacherService teacherService;

    @Autowired
    EvaluationDimensionService evaluationDimensionService;

    //获取教师评价
    @GetMapping("/eval")
    public Result getEvaluationByTeacher_old(@RequestBody Teacher teacher){
        Long teacherId = teacher.getTeacherId();
        List<EvaluationCriterion> evaluations = evaluationService.getEvaluationsByTeacherId_old(teacherId);
        return new Result(evaluations);
    }

    @PostMapping("/evals_old")
    public Result getEvaluationByTeacher_old2(@RequestBody Student student){
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
            List<EvaluationCriterion> evaluations = evaluationService.getEvaluationsByTeacherId_old(teacherId);
            evaluationss.add(evaluations);
        }
        return new Result(evaluationss);
    }

    //显示可以评价的教师
    @PostMapping("/teacherEval")
    public Result getTeacherByCourse(@RequestBody Student student){
        List<Teacher> teachers = teacherService.getTeachersByStudent(student);
        List<EvaluationDimension> evaluationDimensions = evaluationService.getEvaluationDimensions();
        TeacherEvalInfo teacherEvalInfo=new TeacherEvalInfo(teachers, evaluationDimensions);
        return new Result(teacherEvalInfo);
    }

    //提交教师评价
    @PostMapping("/evals")
    public Result evaluateTeacher(@RequestBody TeacherEvaluationsDTO teacherEvaluationsDTO){
        // 调用 Service 层方法保存所有评价记录
        Integer i = teacherService.saveEvaluations(teacherEvaluationsDTO);
        if(i==0){
            throw new BusinessException(ExceptionType.INTERNAL_ERROR,"保存失败");
        }
        return new Result(i,"success");
    }


    //评价教师
    @PostMapping("/eval_old")
    public Result evaluateTeacher_old(@RequestBody Double[] scores, @RequestBody EvaluationCriterion evaluationCriterion, @RequestBody String name){
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

    //教师查看自己评价
    //学生查看教师评价
    @GetMapping("/teacherEval")
    public Result getTeacherEvaluation(@RequestBody Teacher teacher){
        Long teacherId = teacher.getTeacherId();
        List<Evaluation> evaluations = evaluationService.getEvaluationsByTeacherId(teacherId);
        return new Result(evaluations);
    }

    //获取可以评价的教师
    @GetMapping("student/{studentId}/pending-evals")
    public Result pending(@PathVariable Long studentId) {
        List<PendingEvalDTO> pending = scService.getPendingEvaluationsWithNames(studentId);
        return new Result(pending);
    }

    //获取评价维度
    @GetMapping("/eval/dimensions")
    public Result getDimensions() {
        List<EvaluationDimension> dims = evaluationDimensionService.getAllEvaluationDimension();
        return new Result(dims);
    }

    //学生提交评价
    @PostMapping("/eval")
    public Result evaluate(@RequestBody Evaluation evaluation){
        if(evaluation.getEvaluationDetails()==null || evaluation.getTeacherId()==null|| evaluation.getCourseId()==null){
            log.debug("evaluation : {}" ,evaluation);
            throw new BusinessException(ExceptionType.EVAL_ERROR,"评价信息错误");
        }
        Integer i = evaluationService.saveEvaluation(evaluation);
        if(i==0){
            throw new BusinessException(ExceptionType.INTERNAL_ERROR,"保存失败");
        }
        return new Result(i,"success");
    }

    //评价维度分页查询
    @GetMapping("/eval/dimensions/page/{pageNum}/size/{pageSize}")
    public Result getDimensionsPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        PageItem<EvaluationDimension> pageItem = evaluationDimensionService.queryPage(pageNum, pageSize);
        return new Result(pageItem);
    }

    //评价分页查询
    @GetMapping("/evaluations/page/{pageNum}/size/{pageSize}")
    public Result getEvalPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        PageItem<Evaluation> pageItem = evaluationService.queryPage(pageNum, pageSize);
        return new Result(pageItem);
    }

    //删除评价维度
    @DeleteMapping("/eval/dimensions/{id}")
    public Result deleteDimension(@PathVariable Long id) {
        Integer i = evaluationDimensionService.deleteEvaluationDimension(id);
        if (i == 0) {
            throw new BusinessException(ExceptionType.EVAL_DIMENSION_NOT_EXIST, "评价维度不存在");
        }
        return new Result(i, "success");
    }

    //新增评价维度
    @PostMapping("/eval/dimensions")
    public Result addDimension(@RequestBody EvaluationDimension evaluationDimension) {
        Integer i = evaluationDimensionService.getEvaluationDimension(evaluationDimension);
        if (i == 0) {
            throw new BusinessException(ExceptionType.EVAL_DIMENSION_EXIST, "评价维度已存在");
        }
        return new Result(i, "success");
    }

    //修改评价维度
    @PutMapping("/eval/dimensions/{dimensionId}")
    public Result updateDimension(@RequestBody EvaluationDimension evaluationDimension , @PathVariable Long dimensionId) {
        EvaluationDimension evaluationDimension1 = evaluationDimensionService.getEvaluationDimensionByDimensionId(dimensionId);
        if (evaluationDimension1== null || evaluationDimension1.getDimensionId()== null) {
            throw new BusinessException(ExceptionType.EVAL_DIMENSION_NOT_EXIST, "评价维度不存在");
        }
        Integer i = evaluationDimensionService.updateEvaluationDimension(evaluationDimension);
        return new Result(i, "success");
    }

    //查看对应评价的详细信息
    @GetMapping("/eval/{evaluationId}")
    public Result getEvaluationDetail(@PathVariable Long evaluationId){
        //Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
        List<EvaluationDetail> evaluationDetails = evaluationService.getEvaluationDetail(evaluationId);
        return new Result(evaluationDetails);
    }

    //获取该教师的总体平均分（所有维度总体平均），以及每个维度的平均分

    @GetMapping("/stats/teacher/{teacherId}")
    public Result getTeacherStats(@PathVariable Long teacherId) {
        // 返回 DTO 包含：overallAvg（Double） + List< { dimensionName, avgScore } >
        TeacherStatsDTO stats = evaluationService.calculateTeacherStats(teacherId);
        return new Result(stats);
    }

    //分页查询每个学生对该教师的评价概要（包括 evaluationScore 即总体得分）

    @GetMapping("/evals/teacher/{teacherId}/page/{pageNum}/size/{pageSize}")
    public Result listEvaluations(@PathVariable Long teacherId,@PathVariable int pageNum,@PathVariable int pageSize) {
        PageItem<Evaluation> page = evaluationService.listByTeacher(teacherId, pageNum, pageSize);
        return new Result(page);
    }

    //获取某一次评价的详细维度打分

    @GetMapping("/teacher/{teacherId}/evaluations/details/{evaluationId}")
    public Result getEvaluationDetails(@PathVariable Long evaluationId) {
        List<EvaluationDetail> details = evaluationService.getDetails(evaluationId);
        return new Result(details);
    }
}
