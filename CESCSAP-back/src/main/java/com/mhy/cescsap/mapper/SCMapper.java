package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.PendingEvalDTO;
import com.mhy.cescsap.pojo.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SCMapper {
    // 查看学生选课
    @Select("select * from studentcourse where sc_id = #{scId}")
    List<StudentCourse> getStudents(Long scId);
    // 添加学生选课
    @Insert("insert into studentcourse(student_id, student_name,course_id,course_name,teacher_id,teacher_name,evaluate_status) values (#{studentId},#{studentName},#{courseId},#{courseName},#{teacherId},#{teacherName},#{evaluateStatus})")
    Integer addStudentCourse(StudentCourse studentCourse);

    // 查询符合条件的学生选课
    @Select("select * from studentcourse where sc_id = #{scId}")
    List<StudentCourse> selectByCondition(StudentCourse sc);

    @Select("select * from studentcourse where student_id = #{studentId}")
    List<StudentCourse> selectByCondition2(Long studentId);

    //学生课程查重
    @Select("select count(*) from studentcourse where student_id = #{studentId} and course_id = #{courseId}")
    Integer countByStudentAndCourse(Long studentId, Long courseId);

    @Select("select * from  studentcourse where course_id = #{courseId}")
    List<StudentCourse> getStudentsByCourse(Long courseId);

    @Delete("delete from studentcourse where course_id = #{courseId} and student_id = #{studentId}")
    Integer deleteStudentsByCourse(StudentCourse sc);

    @Select("select * from studentcourse where student_id = #{sid} and evaluate_status = 0")
    List<StudentCourse> findPendingEvaluations(Long sid);

    @Select("select * from studentcourse")
    List<StudentCourse> getAllSC();

    //更新所有数据
    @Update("update studentcourse set student_name = #{studentName}, course_name = #{courseName}, teacher_id = #{teacherId},teacher_name = #{teacherName} where sc_id = #{scId}")
    Integer updateSC(StudentCourse sc);

    @Select("SELECT sc.course_id AS courseId, " +
            "c.course_name AS courseName, " +
            "sc.teacher_id AS teacherId, " +
            "t.name AS teacherName " +
            "FROM studentcourse sc " +
            "JOIN course c ON sc.course_id = c.course_id " +
            "JOIN teacher t ON sc.teacher_id = t.teacher_id " +
            "WHERE sc.student_id = #{studentId} " +
            "  AND sc.evaluate_status = 0")
    List<PendingEvalDTO> getPendingEvals(Long studentId);

    @Update("UPDATE studentcourse " +
            "SET evaluate_status = 1 " +
            "WHERE student_id = #{studentId} " +
            "  AND course_id = #{courseId} " +
            "  AND teacher_id = #{teacherId}")
    Integer markEvaluated(Long studentId,Long courseId,Long teacherId);
}
