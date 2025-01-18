package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Course;

import java.util.List;

public interface CourseService {
    //查询所有课程
    List<Course> getCourseList();
    //查询单个课程
    Course getCourseById(Long id);
    //修改单个课程
    Integer updateCourse(Course course);
    //添加单个课程
    Integer addCourse(Course course);
    //删除单个课程
    Integer deleteCourse(Long id);
}
