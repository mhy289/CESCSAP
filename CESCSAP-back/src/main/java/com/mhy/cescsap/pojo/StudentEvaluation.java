package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEvaluation {
    private Long studentEvaluationId;
    private Long studentId;
    private Long evaluationId;
    private Student student;
    private Evaluation evaluation;
}
