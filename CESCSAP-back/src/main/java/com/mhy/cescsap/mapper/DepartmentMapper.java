package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department where department_id = #{departmentId}")
    Department getDepartmentById(Long departmentId);

    @Select("select * from department where department_name = #{department}")
    Department getDepartmentByName(String department);
}
