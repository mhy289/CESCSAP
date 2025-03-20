package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.ClassMapper;
import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassMapper classMapper;

    @Override
    public Class getClass(Long id) {
        return classMapper.getClassByClassId(id);
    }

    @Override
    public List<Class> getAllClass() {
        return classMapper.getAllClasses();
    }

    @Override
    public Integer addClass(Class clazz) {
        return classMapper.addClass(clazz);
    }

    @Override
    public Integer updateClass(Class clazz) {
        return classMapper.updateClass(clazz);
    }

    @Override
    public Integer deleteClass(Long id) {
        return classMapper.deleteClass(id);
    }
}
