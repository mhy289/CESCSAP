package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.EvaluationDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface EvaluationDetailMapper {
    @Select("select * from evaluationdetail where evaluation_id =#{evaluationId}")
    List<EvaluationDetail> getEvaluationDetails(Long evaluationId);

    @Insert("insert into evaluationdetail(evaluation_id, dimension_id, score) VALUES (#{evaluationId},#{dimensionId},#{score})")
    Integer insertEvaluationDetail(EvaluationDetail evaluationDetail);


    //List<EvaluationDetail> getEvaluationDetail(Long );
}
