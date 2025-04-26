package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EvaluationService {
    List<EvaluationCriterion> getEvaluationsByTeacherId_old(Long teacherId);

    List<Evaluation> getEvaluationsByTeacherId(Long teacherId);

    Integer updateEvaluationByTeacher(EvaluationCriterion evaluationCriterion,Double score);

    Integer saveEvaluation(Evaluation evaluation);

    List<EvaluationDimension> getEvaluationDimensions();

    PageItem<Evaluation> queryPage(Integer pageNum, Integer pageSize);

    Evaluation getEvaluationById(Long evaluationId);

    List<EvaluationDetail> getEvaluationDetail(Long evaluationId);


    TeacherStatsDTO calculateTeacherStats(Long teacherId);


    PageItem<Evaluation> listByTeacher(Long teacherId, int pageNum, int pageSize);

    List<EvaluationDetail> getDetails(Long evaluationId);
}
