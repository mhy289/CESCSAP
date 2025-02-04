package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.StudentCourse;
import com.mhy.cescsap.service.SCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SCServiceImpl implements SCService {

    @Autowired
    SCMapper scMapper;

    @Autowired
    StudentMapper studentMapper;

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
    public PageItem<StudentCourse> queryConditionPage(Student student, Integer current, Integer size) {
        Student student1 = studentMapper.selectStudentByName(student.getName());
        PageHelper.startPage(current, size);
        log.info("Student is {}" , student);
        //log.info("sc is {}",sc);
        //Student student = studentMapper.selectStudentByName(sc);
//        if(student==null){
//            log.info("Student not found: {}", sc);
//            return new PageItem<>(0L, null);
//        }

        List<StudentCourse> orderList = scMapper.selectByCondition2(student1.getStudentId());
        log.info("orderList is {}" , orderList);
        Page<StudentCourse> info = (Page<StudentCourse>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }
}
