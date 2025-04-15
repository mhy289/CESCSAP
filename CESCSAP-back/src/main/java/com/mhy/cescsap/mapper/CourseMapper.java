package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Course;
import com.mhy.cescsap.pojo.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 查询课程
    //查询所有课程
    @Select("select * from course")
    List<Course> getCourses();
    //查询单个课程
    @Select("select * from course where course_id = #{courseId}")
    Course getCourseById(Long courseId);
    //修改单个课程
    @Update("update course set course_name = #{courseName}, teacher_id = #{teacherId},teacher_name = #{teacherName}, description = #{description}, credit = #{credit} where course_id = #{courseId}")
    Integer updateCourse(Course course);
    //删除单个课程
    @Delete("delete from course where course_id = #{courseId}")
    Integer deleteCourse(Long courseId);
    //添加单个课程
    @Insert("insert into course(course_name, teacher_id, teacher_name, description, credit) values(#{courseName}, #{teacherId}, #{teacherName}, #{description}, #{credit})")
    Integer addCourse(Course course);

    @Select("select * from course where course_id = #{courseId}")
    List<Course> selectByCondition(Course course);

    @Select("select * from course where teacher_id = #{id}")
    List<Course> getCoursesByTeacherId(Long id);
}
