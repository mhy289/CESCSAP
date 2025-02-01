package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.SCMapper;
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
    public PageItem<StudentCourse> queryConditionPage(StudentCourse sc, Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<StudentCourse> orderList = scMapper.selectByCondition(sc);
        Page<StudentCourse> info = (Page<StudentCourse>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }
}
