package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 考核指标
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCriterion {
    // 考核指标id
    private Long ecId;
    private Long evaluationId;
    // 考核课程id
    private Long courseId;
    private Long teacherId;
    // 考核课程
    private Course course;
    private Evaluation evaluation;
    private Teacher teacher;
}
