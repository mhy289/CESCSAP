package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SCMapper scMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Student> getStudents() {
        return studentMapper.getStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public Integer addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public Integer deleteStudent(Long id) {
        return studentMapper.deleteStudent(id);
    }

    @Override
    public Integer updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public PageItem<Student> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Student> orderList = studentMapper.getStudents();
        long total = ((Page<Student>) orderList).getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public PageItem<Student> queryConditionPage(Student student, Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Student> orderList = studentMapper.selectByCondition2(student);
        Page<Student> info = (Page<Student>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentMapper.getStudentByName(name);
    }

    @Override
    public List<TeacherCourse> getFilterOptions(Student student) {
        String name = student.getName();
        Student student1 = studentMapper.selectStudentByName(name);
        Long studentId = student1.getStudentId();
        List<StudentCourse> studentCourses = scMapper.selectByCondition2(studentId);
        List<TeacherCourse> tclist=new ArrayList<>();
        for(StudentCourse sc : studentCourses){
            Long courseId = sc.getCourseId();
            Course course = courseMapper.getCourseById(courseId);
            TeacherCourse tc=new TeacherCourse(course.getTeacherName(),course.getCourseName());
            tclist.add(tc);
        }
        return tclist;
    }

    @Override
    public Integer updateCommentStatus() {
        return studentMapper.updateEvaluateStatus(1);
    }

    @Override
    public Integer updateCommentStatus(Student student) {
        return studentMapper.updateEvaluateStatus1(student);
    }
}
