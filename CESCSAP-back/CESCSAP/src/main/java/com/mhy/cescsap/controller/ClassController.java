package com.mhy.cescsap.controller;


import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class ClassController {

    @Autowired
    ClassService classService;

    //查询单个班级
    @GetMapping("/class/{id")
    public Result getClass(@PathVariable Long id){
        return new Result(classService.getClass(id));
    }
    //查询所有班级
    @GetMapping("/classes")
    public Result getAllClasses(){
        return new Result(classService.getAllClass());
    }
    //增加一个班级
    @PostMapping("/class")
    public Result addClass(@RequestBody Class clazz){
        return new Result(classService.addClass(clazz));
    }
    //修改一个班级
    @PutMapping("/class")
    public Result updateClass(@RequestBody Class clazz){
        return new Result(classService.updateClass(clazz));
    }
    //删除一个班级
    @DeleteMapping("/class/{id}")
    public Result deleteClass(@PathVariable Long id){
        return new Result(classService.deleteClass(id));
    }
}
