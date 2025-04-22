package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.pojo.Warning;

import java.util.List;

public interface WarningService {
    Integer updateWarningByTeacher(Warning warning);

    Integer updateStudentResponse(Long warningId, String studentResponse);

    List<Warning> getWarningsForStudent(Long studentId);

    List<Warning> getWarningsForTeacher(Long teacherId);

    PageItem<Warning> getWarnings(Integer pageNum, Integer pageSize);
}
