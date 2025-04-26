package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.EvaluationDimension;
import com.mhy.cescsap.pojo.TeacherStatsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EvaluationDimensionMapper {
    //新增评价维度
    @Insert("insert into evaluationdimension(dimension_name, description) VALUES (#{dimensionName},#{description})")
    Integer setEvaluationdimension(EvaluationDimension evaluationDimension);

    //获取所有评价维度
    @Select("select * from evaluationdimension")
    List<EvaluationDimension> getAllEvaluationDimension();

    //删除评价维度
    @Delete("delete from evaluationdimension where dimension_id = #{id}")
    Integer deleteEvaluationDimension(Long id);

    //修改评价维度
    @Update("update evaluationdimension set dimension_name = #{dimensionName}, description = #{description} where dimension_id = #{dimensionId}")
    Integer updateEvaluationDimension(EvaluationDimension evaluationDimension);

    //根据id获取评价维度
    @Select("select * from evaluationdimension where dimension_id = #{dimensionId}")
    EvaluationDimension getEvaluationDimensionByDimensionId(Long dimensionId);


    @Select("""
        SELECT t.dimension_id       AS dimensionId,
               dim.dimension_name   AS dimensionName,
               AVG(t.score)         AS avgScore
        FROM evaluationdetail t
        INNER JOIN evaluation e
          ON t.evaluation_id = e.evaluation_id
        INNER JOIN evaluationdimension dim
          ON t.dimension_id = dim.dimension_id
        WHERE e.teacher_id = #{teacherId}
        GROUP BY t.dimension_id, dim.dimension_name
    """)
    List<TeacherStatsDTO.DimensionAvg> selectAvgByDimensions(Long teacherId);

}
