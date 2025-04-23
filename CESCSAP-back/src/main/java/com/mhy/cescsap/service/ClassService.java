package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Class;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ClassService {
    //查询单个班级
    Class getClass(Long id);

    //查询所有班级
    List<Class> getAllClass();

    //添加班级
    Integer addClass(Class clazz);

    //修改班级
    Integer updateClass(Class clazz);

    //删除班级
    Integer deleteClass(Long id);

    List<Class> getAllClasses();
}
