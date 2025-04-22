package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.*;

import java.util.List;

public interface EvaluationService {
    List<EvaluationCriterion> getEvaluationsByTeacherId_old(Long teacherId);

    List<Evaluation> getEvaluationsByTeacherId(Long teacherId);

    Integer updateEvaluationByTeacher(EvaluationCriterion evaluationCriterion,Double score);

    Integer saveEvaluation(Evaluation evaluation);

    List<EvaluationDimension> getEvaluationDimensions();

    PageItem<Evaluation> queryPage(Integer pageNum, Integer pageSize);

    Evaluation getEvaluationById(Long evaluationId);

    List<EvaluationDetail> getEvaluationDetail(Long evaluationId);
}
