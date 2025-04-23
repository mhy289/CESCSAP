package com.mhy.cescsap.controller;


import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Course;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.service.CourseService;
import com.mhy.cescsap.service.SCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    SCService scService;
    // 课程相关操作
    // 获取单个课程
    @GetMapping("/course/{id}")
    public Result getOneCourse(@PathVariable Long id) {
        return new Result(courseService.getCourseById(id));
    }
    // 获取课程列表
    @GetMapping("/courses")
    public Result getAllCourses() {
        return new Result(courseService.getCourseList());
    }
    // 删除课程
    @DeleteMapping("/courses/{id}")
    public Result deleteCourse(@PathVariable Long id) {
        return new Result(courseService.deleteCourse(id));
    }
    // 编辑课程
    @PutMapping("/courses")
    public Result updateCourse(@RequestBody Course course) {
        return new Result(courseService.updateCourse(course));
    }
    // 新建课程
    @PostMapping("/courses")
    public Result createCourse(@RequestBody Course course) {
        return new Result(courseService.addCourse(course));
    }

    // 条件分页查询
    @PostMapping("/courses/page/{current}/size/{size}")
    public Result getConditionPage(@PathVariable Integer current, @PathVariable Integer size, @RequestBody Course course) {
        return new Result(courseService.queryConditionPage(course, current, size), "查询成功", 200);
    }

    @GetMapping("/courses/page/{current}/size/{size}")
    public Result getConditionPage(@PathVariable Integer current, @PathVariable Integer size) {
        return new Result(courseService.queryPage(current, size), "查询成功", 200);
    }

    //课程批量添加学生
    @PostMapping("/courses/{courseId}/students")
    public Result addStudents(@PathVariable Long courseId, @RequestBody Long[] studentIds) {
        return new Result(courseService.addStudents(courseId, studentIds));
    }

    //获取当前课程已选学生列表
    @GetMapping("/course/{courseId}/students")
    public Result getStudentsByCourse(@PathVariable Long courseId){
        return new Result(courseService.getStudentsByCourse(courseId));
    }

    //获取当前课程未选学生列表
    @GetMapping("/course/{courseId}/no_students")
    public Result getNoStudentsByCourse(@PathVariable Long courseId){
        return new Result(courseService.getNoStudentsByCourse(courseId));
    }

    //批量删除学生
    @DeleteMapping("/courses/{courseId}/students")
    public Result deleteStudentsByCourse(@PathVariable Long courseId, @RequestBody Long[] studentIds){
        if(studentIds.length==0){
            throw new BusinessException(ExceptionType.STU_ERR,"未传入数据");
        }
        log.debug("students is {}",studentIds);
        return new Result(courseService.deleteStudentsByCourse(courseId,studentIds));
    }

    //单独删除学生
    @DeleteMapping("/course/{courseId}/student/{studentId}")
    public Result deleteStudentByc(@PathVariable Long courseId,@PathVariable Long studentId){
        return new Result(courseService.deleteStudentByc(courseId,studentId));
    }

    //验证课程完整性
    @GetMapping("/courses/check")
    public Result checkCourse(){
        Integer allCheck = scService.getAllCheck();
        log.debug("allCheck :{} " ,allCheck);
        if(allCheck==0){
            throw new BusinessException(ExceptionType.COURSE_NOT_COMPLETE,"课程未完成");
        }
        return new Result(allCheck);
    }
}
