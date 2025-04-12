package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.*;

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

    Integer getAllSC();

    //获取可以评价的教师
    List<StudentCourse> getPending(Long sid);

    //验证所有课程完整性
    Integer getAllCheck();

    List<PendingEvalDTO> getPendingEvaluationsWithNames(Long studentId);
}
