package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TeacherService {
    //查询所有教师
    List<Teacher> getTeachers();

    //查询单个教师
    Teacher getTeacherById(Long id);

    //增加一个教师
    Integer addTeacher(Teacher teacher);

    //删除一个教师
    Integer deleteTeacher(Long id);

    //修改一个教师
    Integer updateTeacher(Teacher teacher);

    //教师登录
    Teacher login(Teacher teacher);

    //查询单个教师的评价
    Teacher getTeacherEvaluate(Long id);
}
