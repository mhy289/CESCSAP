package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.pojo.Class;
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

    //查询该学生课程的教师
    List<Teacher> getTeachersByStudent(Student student);

    //提交评价
    Integer saveEvaluations(TeacherEvaluationsDTO teacherEvaluationsDTO);


    PageItem<Teacher> queryPage(Integer current, Integer size);

    //查看教师自己班级的学生
    List<Student> getStudentsByTeacherId(Long teacherId);

    //查看教师自己班级
    List<Class> getClassesByTeacherId(Long teacherId);

    List<Student> getStudentsByClass(Long teacherId, Long classId);

    //查看教师自己课程的学生
    List<StudentCourse> getStudentsByCourse(Long teacherId, Long courseId);

    //计算绩点录入成绩
    Integer saveScores(StudentCourse sc);

    List<Course> getCoursesByTeacherId(Long id);
}
