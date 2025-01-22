package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    //查询教师
    @Select("select * from teacher where teacher_id = #{teacherId}")
    Teacher selectTeacherById(Long id);

    //查询所有教师
    @Select("select * from teacher")
    List<Teacher> selectAllTeachers();

    //增加一个教师
    @Insert("insert into teacher (name, gender, department, birth_date, contact,login_status) values (#{name}, #{gender}, #{department}, #{birthDate}, #{contact},#{loginStatus})")
    Integer insertTeacher(Teacher teacher);

    // 删除一个教师
    @Delete("delete from teacher where teacher_id = #{teacherId}")
    Integer deleteTeacher(Long id);

    // 更新一个教师
    @Update("update teacher set name = #{name}, gender = #{gender}, department = #{department}, birth_date = #{birthDate}, contact = #{contact}, login_status = #{loginStatus} where teacher_id = #{teacherId}")
    Integer updateTeacher(Teacher teacher);

    @Select("select * from teacher where name = #{name}")
    Teacher selectTeacherByName(String teacherName);
}
