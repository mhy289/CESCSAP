package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationSubmitDTO {
    private Long studentId;
    private Long courseId;
    private Long teacherId;
    private String comment;
    private List<EvaluationDetailDTO> details;
    // getter/setter
}