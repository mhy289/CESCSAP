package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.StudentCourse;

import java.util.List;

public interface SCService {
    //查询学生选课
    List<StudentCourse> getSC(Long scId);
    //新增学生选课
    Integer addSC(StudentCourse sc);
}
