package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public Integer deleteTeacher(Integer id) {
        return teacherMapper.deleteTeacher(id);
    }

    @Override
    public Integer updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }
}
