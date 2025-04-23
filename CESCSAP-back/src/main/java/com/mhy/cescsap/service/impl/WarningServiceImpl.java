package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.mapper.WarningMapper;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Warning;
import com.mhy.cescsap.service.WarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WarningServiceImpl implements WarningService {

    @Autowired
    WarningMapper warningMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    CourseMapper courseMapper;



    @Override
    public Integer updateWarningByTeacher(Warning warning) {
        return warningMapper.updateWarningByTeacher(warning);
    }

    @Override
    public Integer updateStudentResponse(Long warningId, String studentResponse) {
        return warningMapper.updateStudentResponse(warningId, studentResponse);
    }

    @Override
    public List<Warning> getWarningsForStudent(Long studentId) {
        return warningMapper.selectWarningsForStudent(studentId);
    }

    @Override
    public List<Warning> getWarningsForTeacher(Long teacherId) {
        return warningMapper.selectWarningsForTeacher(teacherId);
    }

    @Override
    public PageItem<Warning> getWarnings(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Warning> warnings = warningMapper.selectAllWarnings();
        Page<Warning> page = (Page<Warning>) warnings;
        return new PageItem<>(page.getTotal(), warnings);
    }

    @Override
    public PageItem<Warning> getWarningsForTeacherByPage(Long teacherId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Warning> warnings = warningMapper.selectWarningsForTeacher(teacherId);
        for(Warning warning : warnings){
            warning.setStudentName(studentMapper.getStudentById(warning.getStudentId()).getName());
            warning.setTeacherName(teacherMapper.selectTeacherById(warning.getTeacherId()).getName());
            warning.setCourseName(courseMapper.getCourseById(warning.getCourseId()).getCourseName());
        }
        log.debug("----------------------------------------------------------------------");
        log.debug("warnings is {}",warnings);
        log.info("----------------------------------------------------------------------");
        Page<Warning> page = (Page<Warning>) warnings;
        return new PageItem<>(page.getTotal(), warnings);
    }

    @Override
    public PageItem<Warning> getWarningsForStudentByPage(Long studentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Warning> warnings = warningMapper.selectWarningsForStudent(studentId);
        for(Warning warning : warnings){
            warning.setStudentName(studentMapper.getStudentById(warning.getStudentId()).getName());
            warning.setTeacherName(teacherMapper.selectTeacherById(warning.getTeacherId()).getName());
            warning.setCourseName(courseMapper.getCourseById(warning.getCourseId()).getCourseName());
        }
        Page<Warning> page = (Page<Warning>) warnings;
        return new PageItem<>(page.getTotal(), warnings);
    }
}
