package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Student;

import java.util.List;

public interface StudentService {
    //查询学生
    List<Student> getStudents();

    //查询一个学生
    Student getStudentById(Long id);

    //添加一个学生
    Integer addStudent(Student student);

    //删除一个学生
    Integer deleteStudent(Long id);

    //修改一个学生
    Integer updateStudent(Student student);
}
