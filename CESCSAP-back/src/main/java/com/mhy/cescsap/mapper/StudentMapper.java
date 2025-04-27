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
    @Insert("insert into student(account,name,password,department_id,department, gender, birth_date, major, class_id,class_name, contact,gpa,enroll_date,evaluate_status,login_status,role) values(#{account},#{name},#{password},#{departmentId},#{department}, #{gender}, #{birthDate}, #{major}, #{classId},#{className}, #{contact},#{gpa},#{enrollDate},#{evaluateStatus},#{loginStatus},#{role})")
    Integer addStudent(Student student);

    // 删除一个学生
    @Select("delete from student where student_id = #{studentId}")
    Integer deleteStudent(Long studentId);

    // 更新一个学生
    @Update("update student set name = #{name},password = #{password}, gender = #{gender}, birth_date = #{birthDate}, major = #{major}, class_id = #{classId}, contact = #{contact},gpa=#{gpa},evaluate_status = #{evaluateStatus}, login_status = #{loginStatus},role = #{role} where student_id = #{studentId}")
    Integer updateStudent(Student student);

    @Select("select * from student where name = #{name}")
    Student selectStudentByName(String name);

    @Select("select * from student where gender = #{gender}")
    List<Student> selectByCondition(Student student);

    @Select("select * from student where name = #{name}")
    List<Student> selectByCondition2(Student student);

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student getStudentByName(String name);

    //更新所有学生的评价状态
    @Update("update student set evaluate_status = #{evaluateStatus}")
    Integer updateEvaluateStatus(Integer evaluateStatus);

    @Update("update student set evaluate_status = #{evaluateStatus} where student_id = #{studentId}")
    Integer updateEvaluateStatus1(Student student);

    @Select("select * from student where  student_id = #{studentId}")
    Student getNoStudentById(Long studentId);

    @Select("SELECT COUNT(1) FROM student WHERE account = #{account}")
    Boolean existsByAccount(Long account);

    @Update("update student set account = #{account} where student_id = #{studentId}")
    Integer addStudentForAccount(Student student);

    //更新学生GPA
    @Update("update student set gpa = #{gpa} where student_id = #{studentId}")
    Integer updateGPA(Long studentId, Double gpa);

    //更新学生学号
    @Update("update student set student_number = #{studentNumber} where student_id = #{studentId}")
    Integer updateStudentNumber(String studentNumber, Long studentId);

    @Update("update student set department_id = #{departmentId},department = #{department} where student_id = #{studentId}")
    Integer updateStudentDepartment(Student student);

    @Select("SELECT COUNT(*) > 0 FROM student WHERE name = #{name}")
    boolean existsByName(String name);

    @Select("SELECT * FROM student WHERE student_number = #{number}")
    Student selectStudentByNumber(String number);

    @Update("update student set enroll_date = #{enrollDate} where student_id = #{studentId}")
    Integer updateEnrollDate(Student student);

    //检测学生专业

}
