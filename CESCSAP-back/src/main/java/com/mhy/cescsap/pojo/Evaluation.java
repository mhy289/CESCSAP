package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    private Long evaluationId;
    private Double score;
    private String comment;
    private Integer number;
    //权重
    private Double weight;
}
