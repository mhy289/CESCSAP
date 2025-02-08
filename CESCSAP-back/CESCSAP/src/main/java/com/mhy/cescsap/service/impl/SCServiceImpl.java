package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.SCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SCServiceImpl implements SCService {

    @Autowired
    SCMapper scMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<StudentCourse> getSC(Long scId) {
        return scMapper.getStudents(scId);
    }

    @Override
    public Integer addSC(StudentCourse studentCourse) {
        return scMapper.addStudentCourse(studentCourse);
    }

    @Override
    public PageItem<StudentCourse> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<StudentCourse> orderList = scMapper.getStudents(Long.valueOf(current));
        long total = ((Page<StudentCourse>) orderList).getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public PageItem<StudentCourseHistory> queryConditionPage(Student student, Integer current, Integer size) {
        List<StudentCourse> students = scMapper.getStudents(student.getStudentId());
        List<StudentCourseHistory> schs = new ArrayList<>();
        for(StudentCourse studentCourse : students) {
            Long courseId = studentCourse.getCourseId();
            Course course = courseMapper.getCourseById(courseId);
            StudentCourseHistory sch = new StudentCourseHistory();
            sch.setCourseName(course.getCourseName());
            sch.setTeacherName(course.getTeacherName());
            sch.setScore(studentCourse.getScore());
            sch.setGpa(studentCourse.getGpa());
            sch.setCredit(course.getCredit());
            sch.setExamDate(studentCourse.getExamDate());
            schs.add(sch);
        }
        PageHelper.startPage(current, size);
        log.info("Student is {}" , student);
        List<StudentCourseHistory> orderList = schs;
        log.info("orderList is {}" , orderList);
        Page<StudentCourseHistory> info = (Page<StudentCourseHistory>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }
}
