package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.EvaluationDimension;

import java.util.List;

public interface EvaluationDimensionService {
    //新增评价维度
    Integer getEvaluationDimension(EvaluationDimension evaluationDimension);

    //获取所有评价维度
    List<EvaluationDimension> getAllEvaluationDimension();
}
