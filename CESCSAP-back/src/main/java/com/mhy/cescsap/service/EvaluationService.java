package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationCriterion;
import com.mhy.cescsap.pojo.EvaluationDimension;

import java.util.List;

public interface EvaluationService {
    List<EvaluationCriterion> getEvaluationsByTeacherId_old(Long teacherId);

    List<Evaluation> getEvaluationsByTeacherId(Long teacherId);

    Integer updateEvaluationByTeacher(EvaluationCriterion evaluationCriterion,Double score);

    Integer saveEvaluation(Evaluation evaluation);

    List<EvaluationDimension> getEvaluationDimensions();
}
