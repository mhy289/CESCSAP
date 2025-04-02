package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    private Long evaluationId;
    private Long studentId;
    private Long courseId;
    private Long teacherId;
    private Date evaluationTime;
    private Student student;
    private Course course;
    private Teacher teacher;

    private List<EvaluationDetail> evaluationDetails;
}
