package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDetail {
    private Long edId;
    private Long evaluationId;
    private Long dimensionId;
    private Double score;
    private Evaluation evaluation;
    private EvaluationDimension evaluationDimension;
}
