package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.EvaluationDimensionMapper;
import com.mhy.cescsap.pojo.EvaluationDimension;
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
}
