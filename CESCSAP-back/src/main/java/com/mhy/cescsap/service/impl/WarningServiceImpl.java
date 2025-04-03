package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.WarningMapper;
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
}
