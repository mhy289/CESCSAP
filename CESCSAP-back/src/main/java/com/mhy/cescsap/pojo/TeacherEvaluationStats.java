package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEvaluationStats {
    private Long statsId;
    private Long teacherId;
    private Long dimensionId;
    private Double averageScore;
    private Teacher teacher;
    private EvaluationDimension evaluationDimension;
}
