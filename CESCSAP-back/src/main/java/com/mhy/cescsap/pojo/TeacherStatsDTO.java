package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStatsDTO {
    private Double overallAvg;
    private List<DimensionAvg> dimensionAvgs;


    //某个具体维度的平均分
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DimensionAvg {
        private Long dimensionId;
        private String dimensionName;
        private Double avgScore;
    }
}
