package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationCriterion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EvaluationCriterionMapper {
    //查看教师评价
    @Select("select * from evaluationcriterion where teacher_id = #{teacherId}")
    List<EvaluationCriterion> getEvaluationCriterionList(Long teacherId);

    //添加对应课程教师评价
    @Update("update evaluation set score = #{score} where evaluation_id = #{evaluationId}")
    Integer evaluateChange(Evaluation valuation);

    // 获取指定课程指定教师的评价
    @Select("select * from evaluationcriterion where teacher_id = #{teacherId} and course_id = #{courseId}")
    EvaluationCriterion getEvaluationCriterion(Long teacherId, Long courseId);
}