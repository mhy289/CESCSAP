package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Department;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/departments")
    public Result getDepartments(){
        return new Result(departmentService.getDepartments());
    }

    @PostMapping("/department")
    public Result addDepartment(@RequestBody Department department){
        return new Result(departmentService.addDepartment(department));
    }

    @PutMapping("/department")
    public Result updateDepartment(@RequestBody Department department){
        return new Result(departmentService.updateDepartment(department));
    }

    @DeleteMapping("/department/{id}")
    public Result deleteDepartment(@PathVariable Long id){
        return new Result(departmentService.deleteDepartment(id));
    }
}
