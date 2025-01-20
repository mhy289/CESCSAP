package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {
    //查询学生
    @Select("select * from student")
    List<Student> getStudents();

    //查询一个学生
    @Select("select * from student where student_id = #{studentId}")
    Student getStudentById(Long studentId);

    //增加一个学生
    @Insert("insert into student(name, gender, birth_date, major, class_id, contact,login_status) values(#{name}, #{gender}, #{birthDate}, #{major}, #{classId}, #{contact},#{loginStatus})")
    Integer addStudent(Student student);

    // 删除一个学生
    @Select("delete from student where student_id = #{studentId}")
    Integer deleteStudent(Long studentId);

    // 更新一个学生
    @Update("update student set name = #{name}, gender = #{gender}, birth_date = #{birthDate}, major = #{major}, class_id = #{classId}, contact = #{contact}, login_status = #{loginStatus} where student_id = #{studentId}")
    Integer updateStudent(Student student);
}
