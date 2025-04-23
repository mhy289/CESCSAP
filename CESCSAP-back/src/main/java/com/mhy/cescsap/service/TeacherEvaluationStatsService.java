package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Evaluation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeacherEvaluationStatsService {
    //重新计算评分
    Integer recalculateStatisticsForTeacher(Evaluation evaluation);
}
