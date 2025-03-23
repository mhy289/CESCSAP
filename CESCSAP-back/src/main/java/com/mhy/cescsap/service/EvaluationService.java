package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationCriterion;

import java.util.List;

public interface EvaluationService {
    List<EvaluationCriterion> getEvaluationsByTeacherId(Long teacherId);

    Integer updateEvaluationByTeacher(EvaluationCriterion evaluationCriterion,Double score);
}
