package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.DepartmentMapper;
import com.mhy.cescsap.pojo.Department;
import com.mhy.cescsap.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartments() {
        return departmentMapper.getDepartments();
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentMapper.addDepartment(department);
    }

    @Override
    public Integer updateDepartment(Department department) {
        return departmentMapper.updateDepartment(department);
    }

    @Override
    public Integer deleteDepartment(Long id) {
        return departmentMapper.deleteDepartment(id);
    }
}
