package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Evaluation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    @Select("select * from evaluation where teacher_id = #{teacherId}")
    List<Evaluation> getEvaluations(Long teacherId);

    @Insert("insert into evaluation(student_id, course_id, teacher_id, evaluation_time) values (#{studentId},#{courseId},#{teahcerId},#{evaluationTime})")
    Integer insertEvaluation(Evaluation evaluation);
}
