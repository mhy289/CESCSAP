package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {
    //查询教师
    @Select("select * from teacher where teacher_id = #{teacherId}")
    Teacher selectTeacherById(Integer id);

    //增加一个教师
    @Insert("insert into teacher (name, gender, department, birth_date, contact,login_status) values (#{name}, #{gender}, #{department}, #{birthDate}, #{contact},#{loginStatus})")
    Integer insertTeacher(Teacher teacher);
}
