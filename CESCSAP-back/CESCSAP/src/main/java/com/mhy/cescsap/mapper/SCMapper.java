package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SCMapper {
    // 查看学生选课
    @Select("select * from studentcourse where sc_id = #{scId}")
    List<StudentCourse> getStudents(Long scId);
    // 添加学生选课
    @Insert("insert into studentcourse(student_id, course_id,score,exam_date) values (#{studentId}, #{courseId}, #{score}, #{examDate})")
    Integer addStudentCourse(StudentCourse studentCourse);
}
