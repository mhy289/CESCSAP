package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.EvaluationDimension;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EvaluationDimensionMapper {
    //新增评价维度
    @Insert("insert into evaluationdimension(dimension_name, description) VALUES (#{dimensionName},#{description})")
    Integer setEvaluationdimension(EvaluationDimension evaluationDimension);

    //获取所有评价维度
    @Select("select * from evaluationdimension")
    List<EvaluationDimension> getAllEvaluationDimension();
}
