package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.pojo.Course;
import com.mhy.cescsap.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;
    @Override
    public List<Course> getCourseList() {
        return courseMapper.getCourses();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseMapper.getCourseById(id);
    }

    @Override
    public Integer updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public Integer addCourse(Course course) {
        return courseMapper.addCourse(course);
    }

    @Override
    public Integer deleteCourse(Long id) {
        return courseMapper.deleteCourse(id);
    }
}
