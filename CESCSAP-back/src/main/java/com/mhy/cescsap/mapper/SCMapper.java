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
    @Insert("insert into studentcourse(student_id, student_name,course_id,course_name,teacher_id,teacher_name,class_id,class_name,evaluate_status) values (#{studentId},#{name},#{courseId},#{courseName},#{teacherId},#{teacherName},#{classId},#{className},#{evaluateStatus})")
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
    @Update("update studentcourse set student_name = #{name}, course_name = #{courseName}, teacher_id = #{teacherId},teacher_name = #{teacherName},class_id = #{classId},class_name = #{className} where sc_id = #{scId}")
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

    // 标记已评价
    @Update("UPDATE studentcourse " +
            "SET evaluate_status = 1 " +
            "WHERE student_id = #{studentId} " +
            "  AND course_id = #{courseId} " +
            "  AND teacher_id = #{teacherId}")
    Integer markEvaluated(Long studentId, Long courseId, Long teacherId);

    // 查询某班级所有学生（StudentCourse 可左关联 Student 表）
    @Select("SELECT " +
            "sc.sc_id, " +
            "sc.student_id AS sc_student_id, " +  // 使用别名避免字段冲突
            "sc.teacher_id, " +
            "sc.usual_score, " +
            "sc.exam_score, " +
            "sc.score, " +
            "sc.gpa, " +
            "s.student_id, " +
            "s.gender," +
            "s.birth_date,"+
            "s.contact,"+
            "s.gpa,"+
            "s.name, " +
            "s.class_name " +
            "FROM studentcourse sc " +
            "JOIN student s ON sc.student_id = s.student_id " +
            "WHERE sc.teacher_id = #{teacherId} AND s.class_id = #{classId}")
    List<StudentCourse> selectStudentsByClass(Long teacherId, Long classId);

    //查询该教师所有课程下的选课学生
    @Select("SELECT sc.*, s.* "
            + "FROM studentcourse sc "
            + "JOIN student s ON sc.student_id = s.student_id "
            + "WHERE sc.teacher_id = #{teacherId} AND sc.course_id = #{courseId}")
    @Results(/* 同上 */)
    List<StudentCourse> selectStudentsByCourse(@Param("teacherId") Long teacherId,
                                               @Param("courseId") Long courseId);

    //更新成绩
    @Update("UPDATE studentcourse "
            + "SET usual_score = #{usualScore}, exam_score = #{examScore}, "
            + "    usual_rate = #{usualRate}, exam_rate = #{examRate}, "
            + "    score = #{score}, gpa = #{gpa}, exam_date = #{examDate} "
            + "WHERE sc_id = #{scId}")
    Integer updateScores(StudentCourse sc);


    @Select("select * from studentcourse where course_name = #{courseName}")
    List<StudentCourse> getStudentsByCourseName(String courseName);

    //查询所有有效成绩
    @Select("select gpa from studentcourse where student_id = #{studentId} and gpa is not null")
    List<Double> selectValidGPAs(Long studentId);
}
