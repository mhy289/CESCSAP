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
    private Long evaluationCriterionId;
    // 考核指标名称
    private String evaluationCriterionName;
    // 考核指标分值
    private Integer evaluationCriterionScore;
    // 考核指标描述
    private String evaluationCriterionDescription;
    // 考核课程id
    private Long courseId;
    // 考核课程
    private Course course;
    //权重
    private Double weight;
}
