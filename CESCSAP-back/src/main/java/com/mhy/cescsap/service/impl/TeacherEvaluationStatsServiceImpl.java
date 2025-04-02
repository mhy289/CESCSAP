package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.EvaluationDetailMapper;
import com.mhy.cescsap.mapper.EvaluationMapper;
import com.mhy.cescsap.mapper.TeacherEvaluationStatsMapper;
import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationDetail;
import com.mhy.cescsap.pojo.TeacherEvaluationStats;
import com.mhy.cescsap.service.TeacherEvaluationStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherEvaluationStatsServiceImpl implements TeacherEvaluationStatsService {

    @Autowired
    TeacherEvaluationStatsMapper teacherEvaluationStatsMapper;

    @Autowired
    EvaluationDetailMapper evaluationDetailMapper;

    @Autowired
    EvaluationMapper evaluationMapper;

    @Override
    public Integer recalculateStatisticsForTeacher(Evaluation evaluation) {
        // 查询该教师所有评价明细
        Long teacherId = evaluation.getTeacherId();
        List<Evaluation> evaluations = evaluationMapper.getEvaluations(teacherId);
        List<EvaluationDetail> evaluationDetails = new ArrayList<>();
        for (Evaluation evaluation1 : evaluations) {
            Long evaluationId = evaluation1.getEvaluationId();
            List<EvaluationDetail> evaluationDetails0 = evaluationDetailMapper.getEvaluationDetails(evaluationId);
            evaluationDetails.addAll(evaluationDetails0);
        }
        //List<EvaluationDetail> evaluationDetails = evaluation.getEvaluationDetails();
        // 用 Map 分组， key 为维度ID， value 为打分列表
        Map<Long, List<Double>> socreMap = new HashMap<>();
        for (EvaluationDetail evaluationDetail : evaluationDetails) {
            socreMap.computeIfAbsent(evaluationDetail.getDimensionId(), k -> new ArrayList<>()).add(evaluationDetail.getScore());
        }
        // 计算每个维度的平均分，并更新或插入统计记录
        for (Map.Entry<Long, List<Double>> entry : socreMap.entrySet()) {
            Long dimensionId = entry.getKey();
            List<Double> scores = entry.getValue();
            Double sum = 0.0;
            for (Double score : scores) {
                sum += score;
            }
            double avg = scores.size() > 0 ? sum / scores.size() : 0;
            // 封装统计对象，注意可以查询评价维度名称等信息
            TeacherEvaluationStats stats = new TeacherEvaluationStats();
            stats.setTeacherId(teacherId);
            stats.setDimensionId(dimensionId);
            stats.setAverageScore(avg);
            // 更新统计记录：如果已存在则更新，否则插入
            TeacherEvaluationStats ex = teacherEvaluationStatsMapper.selectByTeacherIdAndDimensionId(teacherId, dimensionId);
            Integer i = 0;
            if (ex != null) {
                stats.setStatsId(ex.getStatsId());
                i = teacherEvaluationStatsMapper.updateTeacherEvaluationStats(stats);
            } else {
                i = teacherEvaluationStatsMapper.insertTeacherEvaluationStats(stats);
            }
            return i;
        }
        return null;
    }
}
