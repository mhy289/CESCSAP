package com.mhy.cescsap.controller;


import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class CourseController {

    @Autowired
    CourseService courseService;
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
    public Result updateCourse(@RequestBody com.mhy.cescsap.pojo.Course course) {
        return new Result(courseService.updateCourse(course));
    }
    // 新建课程
    @PostMapping("/courses")
    public Result createCourse(@RequestBody com.mhy.cescsap.pojo.Course course) {
        return new Result(courseService.addCourse(course));
    }
}
