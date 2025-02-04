package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

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
}
