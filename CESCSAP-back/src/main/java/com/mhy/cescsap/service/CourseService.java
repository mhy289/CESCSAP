package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Course;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.StudentCourse;

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
    // 查询课程分页
    Object queryConditionPage(Course course, Integer current, Integer size);

    // 课程批量添加学生
    Integer addStudents(Long courseId, Long[] studentIds);

    PageItem<Course> queryPage(Integer current, Integer size);

    List<Student> getStudentsByCourse(Long courseId);

    List<Student> getNoStudentsByCourse(Long courseId);

    Integer deleteStudentsByCourse(Long courseId, Long[] studentIds);

    Integer deleteStudentByc(Long courseId, Long studentId);
}

