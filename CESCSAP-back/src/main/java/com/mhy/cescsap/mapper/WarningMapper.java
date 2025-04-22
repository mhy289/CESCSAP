package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Warning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarningMapper {

    @Update("update warning set student_id = #{studentId},course_id = #{courseId},teacher_id = #{teacherId},warning_content = #{warningContent},send_date = #{sendDate} where warning_id = #{warningId}")
    Integer updateWarningByTeacher(Warning warning);

    @Update("update warning set student_response = #{studentResponse} where warning_id = #{warningId}")
    Integer updateStudentResponse(Long warningId, String studentResponse);

    @Select("select * from warning where student_id = #{studentId}")
    List<Warning> selectWarningsForStudent(Long studentId);

    @Select("select * from warning where teacher_id = #{teacherId}")
    List<Warning> selectWarningsForTeacher(Long teacherId);

    @Select("select * from warning")
    List<Warning> selectAllWarnings();
}
