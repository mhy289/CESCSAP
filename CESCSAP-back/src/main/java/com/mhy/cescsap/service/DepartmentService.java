package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Department;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DepartmentService {
    List<Department> getDepartments();


    Department addDepartment(Department department);

    Integer updateDepartment(Department department);


    Integer deleteDepartment(Long id);
}
