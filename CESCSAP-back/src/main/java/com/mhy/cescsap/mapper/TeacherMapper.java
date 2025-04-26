package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.StudentCourse;
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
    @Insert("insert into teacher (account,name, gender, department, birth_date, contact,login_status,role) values (#{account},#{name}, #{gender}, #{department}, #{birthDate}, #{contact},#{loginStatus},#{role})")
    Integer insertTeacher(Teacher teacher);

    // 删除一个教师
    @Delete("delete from teacher where teacher_id = #{teacherId}")
    Integer deleteTeacher(Long id);

    // 更新一个教师
    @Update("update teacher set name = #{name}, gender = #{gender}, department = #{department}, birth_date = #{birthDate}, contact = #{contact}, login_status = #{loginStatus},role = #{role} where teacher_id = #{teacherId}")
    Integer updateTeacher(Teacher teacher);

    @Select("select * from teacher where name = #{name}")
    Teacher selectTeacherByName(String teacherName);

    @Select("SELECT COUNT(1) FROM teacher WHERE account = #{account}")
    Boolean existsByAccount(Long account);

    @Update("update teacher set teacher_number = #{teacherNumber} where teacher_id = #{teacherId}")
    Integer updateTeacherNumber(String teacherNumber, Long teacherId);

    @Update("update teacher set department = #{department},department_id = #{departmentId} where teacher_id = #{teacherId}")
    Integer updateTeacherDepartment(Teacher teacher);

    @Select("select * from teacher where account = #{account}")
    Teacher selectTeacherByAccount(Long account);

    @Select("select * from teacher where teacher_number = #{number}")
    Teacher selectTeacherByNumber(String number);

    //Integer updateScores(StudentCourse );
}
