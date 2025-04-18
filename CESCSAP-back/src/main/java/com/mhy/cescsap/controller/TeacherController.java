package com.mhy.cescsap.controller;

import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.ClassService;
import com.mhy.cescsap.service.EvaluationService;
import com.mhy.cescsap.service.TeacherEvaluationStatsService;
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

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    TeacherEvaluationStatsService teacherEvaluationStatsService;

    @Autowired
    //CourseMapper courseMapper;
    ClassService classService;

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
    public Result addTeacher(@RequestBody Teacher teacher){
        if(teacher.getName() == null){
            throw new BusinessException(ExceptionType.TEACHER_NAME_NULL,"教师名称不能为空");
        }
        return new Result(teacherService.addTeacher(teacher));
    }

    // 删除一个教师
    @DeleteMapping("/teacher/{id}")
    public Result deleteTeacher(@PathVariable Long id){
        return new Result(teacherService.deleteTeacher(id));
    }

    // 修改一个教师
    @PutMapping("/teacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        return new Result(teacherService.updateTeacher(teacher));
    }

    //查询单个教师评价
    @GetMapping("/teacher/{id}/evaluate")
    public Result getTeacherEvaluate(@PathVariable Long id){
        return new Result(teacherService.getTeacherEvaluate(id));
    }

    //将学生评价统计后存入
    @PostMapping("/teacher/evaluate/statistics")
    public Result addTeacherEvaluateStatistics(@RequestBody Evaluation evaluation){
            Integer t = evaluationService.saveEvaluation(evaluation);
            if(t == null || t <=0){
                throw new BusinessException(ExceptionType.EVAL_SAVE_ERR,"评价存入失败");
            }
            Integer i = teacherEvaluationStatsService.recalculateStatisticsForTeacher(evaluation);
            if(i == null || i <=0){
                throw new BusinessException(ExceptionType.EVAL_STATISTICS_ERR,"评价统计失败");
            }
            return new Result("评价存入成功");
    }

    // 分页获取教师
    @GetMapping("/teachers/page/{current}/size/{size}")
    public Result getTeachersByPage(@PathVariable Integer current, @PathVariable Integer size){
        return new Result(teacherService.queryPage(current,size));
    }

    //获取所有班级
    @GetMapping("/teacher/classes")
    public Result getAllClasses(){
        return new Result(classService.getAllClasses());
    }

    // 获取教师所带班级
    @GetMapping("/teacher/classes/{id}")
    public Result getClassesByTeacherId(@PathVariable Long id){
        return new Result(teacherService.getClassesByTeacherId(id));
    }

    // 获取教师所带课程
    @GetMapping("/teacher/courses/{id}")
    public Result getCoursesByTeacherId(@PathVariable Long id){
        return new Result(teacherService.getCoursesByTeacherId(id));
    }

    // 获取教师所带学生
    @GetMapping("/teacher/{id}/students")
    public Result getStudentsByTeacherId(@PathVariable Long id){
        return new Result(teacherService.getStudentsByTeacherId(id));
    }

    // 获取教师所带学生2
    @GetMapping("/teacher/{teacherId}/classes/{classId}/students")
    public Result getStudentsByTeacherId2(@PathVariable Long teacherId,@PathVariable Long classId){
        return new Result(teacherService.getStudentsByClass(teacherId,classId));
    }

    // 获取教师自己课程的学生
    @GetMapping("/teacher/{teacherId}/courses/{courseId}/students")
    public Result getStudentsByCourse(@PathVariable Long teacherId,@PathVariable Long courseId) {
        //Long tid = AuthService.currentTeacherId();
        return new Result(teacherService.getStudentsByCourse(teacherId, courseId));
    }

    //对选课的学生进行成绩保存
    @PostMapping("/teacher/students/score")
    public Result saveScores(@RequestBody StudentCourse sc) {
        return new Result(teacherService.saveScores(sc),"成绩保存成功");
    }

}
