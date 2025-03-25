package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.EvaluationCriterionMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationCriterion;
import com.mhy.cescsap.pojo.Teacher;
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

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    EvaluationCriterionMapper ecMapper;

    @Override
    public List<EvaluationCriterion> getEvaluationsByTeacherId(Long teacherId) {
        List<EvaluationCriterion> evaluationCriterionList = ECMapper.getEvaluationCriterionList(teacherId);
        for(EvaluationCriterion evaluationCriterion : evaluationCriterionList){
            evaluationCriterion.setTeacher(teacherMapper.selectTeacherById(evaluationCriterion.getTeacherId()));
            evaluationCriterion.setCourse(courseMapper.getCourseById(evaluationCriterion.getCourseId()));
            Long ecId = evaluationCriterion.getEcId();
            List<Evaluation> evaluations = ecMapper.getEvaluationsByecId(ecId);
            evaluationCriterion.setEvaluation(evaluations);
            //evaluationCriterionList.add(evaluationCriterion);
        }
        return evaluationCriterionList;
    }

    @Override
    public Integer updateEvaluationByTeacher(EvaluationCriterion ec,Double s) {
        //Double score = ec.getEvaluation().getScore();
        Long teacherId = ec.getTeacherId();
        Long courseId = ec.getCourseId();
        EvaluationCriterion evaluationCriterion = ECMapper.getEvaluationCriterion(teacherId, courseId);
        List<Evaluation> evaluations = evaluationCriterion.getEvaluation();
        for(Evaluation evaluation : evaluations){
            if(evaluation.getNumber()==0 || evaluation.getScore()==null){
                evaluation.setNumber(1);
                evaluation.setScore(s);
                if(evaluation.getEvaluationId()!=null) {
                    return ECMapper.evaluateChange(evaluation);
                }else{
                    throw new BusinessException(ExceptionType.EVAL_ERROR,"添加评价错误");
                }
            }else{
                evaluation.setScore((evaluation.getScore()*evaluation.getNumber()+s)/(evaluation.getNumber()+1));
                evaluation.setNumber(evaluation.getNumber()+1);
                if(evaluation.getEvaluationId()!=null) {
                    return ECMapper.evaluateChange(evaluation);
                }else{
                    throw new BusinessException(ExceptionType.EVAL_ERROR,"添加评价错误");
                }
            }
        }
        return null;
    }
}
