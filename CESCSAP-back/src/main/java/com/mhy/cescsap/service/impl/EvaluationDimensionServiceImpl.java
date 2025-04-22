package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.EvaluationDimensionMapper;
import com.mhy.cescsap.pojo.EvaluationDimension;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.service.EvaluationDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationDimensionServiceImpl implements EvaluationDimensionService {

    @Autowired
    EvaluationDimensionMapper evaluationDimensionMapper;

    @Override
    public Integer getEvaluationDimension(EvaluationDimension evaluationDimension) {
        return evaluationDimensionMapper.setEvaluationdimension(evaluationDimension);
    }

    @Override
    public List<EvaluationDimension> getAllEvaluationDimension() {
        return evaluationDimensionMapper.getAllEvaluationDimension();
    }

    @Override
    public PageItem<EvaluationDimension> queryPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvaluationDimension> evaluationDimensionList = evaluationDimensionMapper.getAllEvaluationDimension();
        Page<EvaluationDimension> pageItem = (Page<EvaluationDimension>) evaluationDimensionList;
        return new PageItem<>(pageItem.getTotal(), evaluationDimensionList);
    }

    @Override
    public Integer deleteEvaluationDimension(Long id) {
        return evaluationDimensionMapper.deleteEvaluationDimension(id);
    }

    @Override
    public Integer updateEvaluationDimension(EvaluationDimension evaluationDimension) {
        return evaluationDimensionMapper.updateEvaluationDimension(evaluationDimension);
    }

    @Override
    public EvaluationDimension getEvaluationDimensionByDimensionId(Long dimensionId) {
        return evaluationDimensionMapper.getEvaluationDimensionByDimensionId(dimensionId);
    }
}
