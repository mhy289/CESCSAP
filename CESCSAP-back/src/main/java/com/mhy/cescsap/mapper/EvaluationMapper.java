package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Evaluation;
import com.mhy.cescsap.pojo.EvaluationDimension;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    @Select("select * from evaluation where teacher_id = #{teacherId}")
    List<Evaluation> getEvaluations(Long teacherId);

    @Insert("insert into evaluation(student_id, course_id, teacher_id, comment, evaluation_time) values (#{studentId},#{courseId},#{teacherId},#{comment},#{evaluationTime})")
    @Options(useGeneratedKeys = true, keyProperty = "evaluationId", keyColumn = "evaluationId")
    Integer insertEvaluation(Evaluation evaluation);

    @Select("select * from evaluationdimension")
    List<EvaluationDimension> getEvaluationDimensions();

    @Select("select * from evaluation")
    List<Evaluation> getAllEvaluation();

    @Select("select * from evaluation where evaluation_id = #{evaluationId}")
    Evaluation getEvaluationById(Long evaluationId);

    @Select("""
        SELECT AVG(t.evaluation_score) 
        FROM evaluation t
        WHERE t.teacher_id = #{teacherId}
    """)
    Double selectOverallAvgByTeacher(Long teacherId);

    @Update("update evaluation set evaluation_score = #{evaluationScore} where evaluation_id = #{evaluationId}")
    Integer updateEvaluationScore(Evaluation evaluation);
}
