package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.*;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    EvaluationMapper evaluationMapper;

    @Autowired
    EvaluationDetailMapper evaluationDetailMapper;

    @Autowired
    SCMapper scMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    EvaluationDimensionMapper evaluationDimensionMapper;

    @Override
    public List<EvaluationCriterion> getEvaluationsByTeacherId_old(Long teacherId) {
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
    public List<Evaluation> getEvaluationsByTeacherId(Long teacherId) {
        return null;
    }

    @Override
    public Integer updateEvaluationByTeacher(EvaluationCriterion ec,Double s) {
//        //Double score = ec.getEvaluation().getScore();
//        Long teacherId = ec.getTeacherId();
//        Long courseId = ec.getCourseId();
//        EvaluationCriterion evaluationCriterion = ECMapper.getEvaluationCriterion(teacherId, courseId);
//        List<Evaluation> evaluations = evaluationCriterion.getEvaluation();
//        for(Evaluation evaluation : evaluations){
//            if(evaluation.getNumber()==0 || evaluation.getScore()==null){
//                evaluation.setNumber(1);
//                evaluation.setScore(s);
//                if(evaluation.getEvaluationId()!=null) {
//                    return ECMapper.evaluateChange(evaluation);
//                }else{
//                    throw new BusinessException(ExceptionType.EVAL_ERROR,"添加评价错误");
//                }
//            }else{
//                evaluation.setScore((evaluation.getScore()*evaluation.getNumber()+s)/(evaluation.getNumber()+1));
//                evaluation.setNumber(evaluation.getNumber()+1);
//                if(evaluation.getEvaluationId()!=null) {
//                    return ECMapper.evaluateChange(evaluation);
//                }else{
//                    throw new BusinessException(ExceptionType.EVAL_ERROR,"添加评价错误");
//                }
//            }
//        }
        return null;
    }

    @Override
    public Integer saveEvaluation(Evaluation evaluation) {
        evaluation.setEvaluationTime(new Date());
        //evaluation.setEvaluateStatus(1);
        log.debug("---------------------------");
        log.debug("evaluation:{}",evaluation);
        Integer i = evaluationMapper.insertEvaluation(evaluation);
        //设置该学生课程评价完成
        //scService.updateCommentStatus(evaluation.getStudentId(), evaluation.getCourseId());
        Integer j = scMapper.markEvaluated(evaluation.getStudentId(),evaluation.getCourseId(),evaluation.getTeacherId());
        if(evaluation.getEvaluationDetails()!=null) {
            for (EvaluationDetail evaluationDetail : evaluation.getEvaluationDetails()) {
                evaluationDetail.setEvaluationId(Long.valueOf(i));
                Integer i1 = evaluationDetailMapper.insertEvaluationDetail(evaluationDetail);
                if (i1 == null || i1 <= 0) {
                    throw new BusinessException(ExceptionType.EVAL_DTL_ERR, "评价细节提交错误");
                }
            }
        }
        return i;
    }

    @Override
    public List<EvaluationDimension> getEvaluationDimensions() {
        // 获取所有评价维度
        List<EvaluationDimension>  evaluationDimensions = evaluationMapper.getEvaluationDimensions();
        if(evaluationDimensions == null || evaluationDimensions.isEmpty()){
            throw new BusinessException(ExceptionType.EVAL_DIM_ERR, "获取评价维度错误");
        }
        return evaluationDimensions;
    }

    @Override
    public PageItem<Evaluation> queryPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Evaluation> evaluations = evaluationMapper.getAllEvaluation();
        return getEvaluationPageItem(evaluations);
    }

    @Override
    public Evaluation getEvaluationById(Long evaluationId) {
        return evaluationMapper.getEvaluationById(evaluationId);

    }

    @Override
    public List<EvaluationDetail> getEvaluationDetail(Long evaluationId) {
        List<EvaluationDetail> evaluationDetails = evaluationDetailMapper.getEvaluationDetails(evaluationId);
        for(EvaluationDetail evaluationDetail : evaluationDetails){
            evaluationDetail.setDimensionName(evaluationDimensionMapper.getEvaluationDimensionByDimensionId(evaluationDetail.getDimensionId()).getDimensionName());
        }
        return evaluationDetails;
    }

    @Override
    public TeacherStatsDTO calculateTeacherStats(Long teacherId) {
        // overall average
        Double overall = evaluationMapper.selectOverallAvgByTeacher(teacherId);
        // 按维度查询平均
        List<TeacherStatsDTO.DimensionAvg> dims = evaluationDimensionMapper.selectAvgByDimensions(teacherId);
        return new TeacherStatsDTO(overall, dims);
    }

    @Override
    public PageItem<Evaluation> listByTeacher(Long teacherId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Evaluation> list = evaluationMapper.getEvaluations(teacherId);
        return getEvaluationPageItem(list);
    }

    private PageItem<Evaluation> getEvaluationPageItem(List<Evaluation> list) {
        for(Evaluation evaluation : list){
            evaluation.setStudentName(studentMapper.getStudentById(evaluation.getStudentId()).getName());
            evaluation.setTeacherName(teacherMapper.selectTeacherById(evaluation.getTeacherId()).getName());
            evaluation.setCourseName(courseMapper.getCourseById(evaluation.getCourseId()).getCourseName());
        }
        Page<Evaluation> page = (Page<Evaluation>) list;
        return new PageItem<>(page.getTotal(), list);
    }

    @Override
    public List<EvaluationDetail> getDetails(Long evaluationId) {
        List<EvaluationDetail> evaluationDetails = evaluationDetailMapper.getEvaluationDetails(evaluationId);
        for(EvaluationDetail evaluationDetail : evaluationDetails){
            evaluationDetail.setDimensionName(evaluationDimensionMapper.getEvaluationDimensionByDimensionId(evaluationDetail.getDimensionId()).getDimensionName());
        }
        return evaluationDetails;
    }
}
