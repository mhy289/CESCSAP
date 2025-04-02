package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.TeacherEvaluationStatsMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.pojo.TeacherEvaluationStats;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherEvaluationStatsMapper teacherEvaluationStatsMapper;

    @Override
    public List<Teacher> getTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public Integer deleteTeacher(Long id) {
        return teacherMapper.deleteTeacher(id);
    }

    @Override
    public Integer updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher login(Teacher teacher) {
        //Long teacherId = teacher.getTeacherId();
        String name = teacher.getName();
        Teacher teacher1 = teacherMapper.selectTeacherByName(name);
        log.info("Teacher is {}",teacher1);
        if (teacher1!=null && teacher1.getPassword().equals(teacher.getPassword())) {
            log.info("User is {}",teacher1);
            return teacher1;
        } else {
            return null;
        }
    }

    @Override
    public Teacher getTeacherEvaluate(Long id) {
        List<TeacherEvaluationStats> teacherEvaluationStatsByTeacherId = teacherEvaluationStatsMapper.getTeacherEvaluationStatsByTeacherId(id);
        Teacher teacher = teacherMapper.selectTeacherById(id);
        teacher.setTeacherEvaluationStats(teacherEvaluationStatsByTeacherId);
        return teacher;
    }
}
