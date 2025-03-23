package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.EvaluationCriterionMapper;
import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationCriterion;
import com.mhy.cescsap.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationCriterionMapper ECMapper;

    @Override
    public List<EvaluationCriterion> getEvaluationsByTeacherId(Long teacherId) {
        return ECMapper.getEvaluationCriterionList(teacherId);
    }

    @Override
    public Integer updateEvaluationByTeacher(EvaluationCriterion ec,Double s) {
        //Double score = ec.getEvaluation().getScore();
        Long teacherId = ec.getTeacherId();
        Long courseId = ec.getCourseId();
        EvaluationCriterion evaluationCriterion = ECMapper.getEvaluationCriterion(teacherId, courseId);
        Double score = evaluationCriterion.getEvaluation().getScore();
        if(evaluationCriterion.getEvaluation().getNumber()==null || evaluationCriterion.getEvaluation().getNumber()==0){
            ec.getEvaluation().setNumber(1);
            ec.getEvaluation().setScore(s);
            return ECMapper.evaluateChange(ec.getEvaluation());
        }else{
            ec.getEvaluation().setScore(score*(evaluationCriterion.getEvaluation().getNumber()-1)+s);
            ec.getEvaluation().setNumber(evaluationCriterion.getEvaluation().getNumber()+1);
            return ECMapper.evaluateChange(ec.getEvaluation());
        }
    }
}
