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
        // 在查询前启动分页，确保分页生效
        PageHelper.startPage(current, size);
        log.info("Student is {}" , student);
        // 执行分页查询，此时返回的List实际上是Page类型
        List<StudentCourse> students = scMapper.selectByCondition2(student.getStudentId());
        log.debug("Student is {}",students);

        // 将结果强制转换为Page以获取分页信息
        Page<StudentCourse> page = (Page<StudentCourse>) students;

        List<StudentCourseHistory> schs = new ArrayList<>();
        for (StudentCourse studentCourse : students) {
            Course course = courseMapper.getCourseById(studentCourse.getCourseId());
            StudentCourseHistory sch = new StudentCourseHistory();
            sch.setCourseName(course.getCourseName());
            sch.setTeacherName(course.getTeacherName());
            sch.setScore(studentCourse.getScore());
            sch.setGpa(studentCourse.getGpa());
            sch.setCredit(course.getCredit());
            sch.setExamDate(studentCourse.getExamDate());
            schs.add(sch);
            log.debug("sch is {}",sch);
        }
        log.debug("schs is {}",schs);
        log.debug("pahe");
        // 使用分页数据构造返回结果
        return new PageItem<>(page.getTotal(), schs);
    }
}
