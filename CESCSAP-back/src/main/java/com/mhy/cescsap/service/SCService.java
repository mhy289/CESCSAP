package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.StudentCourse;
import com.mhy.cescsap.pojo.StudentCourseHistory;

import java.util.List;

public interface SCService {
    //查询学生选课
    List<StudentCourse> getSC(Long scId);
    //新增学生选课
    Integer addSC(StudentCourse sc);

    //分页查询
    Object queryPage(Integer current, Integer size);

    //条件分页查询
    PageItem<StudentCourseHistory> queryConditionPage(Student student, Integer current, Integer size);

    Object queryStudentCourse(Student student);

    Integer refreshGPA(Student student);

    //查询学生所有课程
    List<StudentCourse> getAllSCByStudentId(Long studentId);
}
