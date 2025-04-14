package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {
    //查询单个班级
    @Select("select * from class where class_id = #{classId}")
    Class getClassByClassId(Long classId);
    //查询所有班级
    @Select("select * from class")
    List<Class> getAllClasses();
    //添加单个班级
    @Insert("insert into class(class_id,class_name,major,teacher_id) values (#{classId},#{className},#{major},#{teacherId})")
    Integer addClass(Class clazz);
    //删除单个班级
    @Delete("delete from class where class_id = #{classId}")
    Integer deleteClass(Long classId);
    //修改单个班级
    @Update("update class set class_name = #{className}, major = #{major}, teacher_id = #{teacherId} where class_id = #{classId}")
    Integer updateClass(Class clazz);

    //检查班级是否存在
    @Select("select COUNT(*) from class where major = #{major}")
    Integer checkMajor(String major);

    //. 查询该教师所带班级
    @Select("SELECT class_id AS classId, class_name AS className, major, teacher_id AS teacherId "
            + "FROM class "
            + "WHERE teacher_id = #{teacherId}")
    List<Class> selectClassesByTeacher(Long teacherId);

    //查询该教师所带班级学生
    @Select("SELECT s.* "
            + "FROM class c "
            + "JOIN student s ON c.class_id = s.class_id "
            + "WHERE c.teacher_id = #{teacherId}")
    List<Student> selectStudentsByTeacher(Long teacherId);

    //查询该教师所带班级学生
    @Select("select * from class where class_name = #{className}")
    Class getClassByName(String className);
}
