package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.TeacherEvaluationStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherEvaluationStatsMapper {

    @Select("select * from  teacherevaluationstats where teacher_id = #{teacherId}")
    List<TeacherEvaluationStats> getTeacherEvaluationStatsByTeacherId(Long teacherId);

    @Select("select * from teacherevaluationstats where teacher_id =#{teacherId} and dimension_id = #{dimensionId}")
    TeacherEvaluationStats selectByTeacherIdAndDimensionId(Long teacherId, Long dimensionId);

    @Update("update teacherevaluationstats set teacher_id = #{teacherId},dimension_id = #{dimensionId},average_score = #{averageScore} where stats_id = #{statsId}")
    Integer updateTeacherEvaluationStats(TeacherEvaluationStats stats);

    @Insert("insert into teacherevaluationstats(teacher_id, dimension_id, average_score) VALUES (#{teacherId},#{dimensionId},#{averageScore})")
    Integer insertTeacherEvaluationStats(TeacherEvaluationStats stats);
}
