package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.EvaluationDimension;
import com.mhy.cescsap.pojo.PageItem;

import java.util.List;

public interface EvaluationDimensionService {
    //新增评价维度
    Integer getEvaluationDimension(EvaluationDimension evaluationDimension);

    //获取所有评价维度
    List<EvaluationDimension> getAllEvaluationDimension();

    //分页获取评价维度
    PageItem<EvaluationDimension> queryPage(Integer pageNum, Integer pageSize);

    //删除评价维度
    Integer deleteEvaluationDimension(Long id);

    //修改评价维度
    Integer updateEvaluationDimension(EvaluationDimension evaluationDimension);

    //根据id获取评价维度
    EvaluationDimension getEvaluationDimensionByDimensionId(Long dimensionId);
}
