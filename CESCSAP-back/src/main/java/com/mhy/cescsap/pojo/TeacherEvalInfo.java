package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEvalInfo {
    private List<Teacher> teacherList;
    private List<EvaluationDimension> evaluationDimensionLIst;
}
