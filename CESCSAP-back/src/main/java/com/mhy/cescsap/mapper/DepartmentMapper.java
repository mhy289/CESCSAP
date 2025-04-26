package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department where department_id = #{departmentId}")
    Department getDepartmentById(Long departmentId);

    @Select("select * from department where department_name = #{department}")
    Department getDepartmentByName(String department);

    @Select("select * from department")
    List<Department> getDepartments();

    @Insert("insert into department(department_name) values(#{departmentName})")
    Department addDepartment(Department department);

    @Update("update department set department_name = #{departmentName} where department_id = #{departmentId}")
    Integer updateDepartment(Department department);

    @Delete("delete from department where department_id = #{id}")
    Integer deleteDepartment(Long id);
}
