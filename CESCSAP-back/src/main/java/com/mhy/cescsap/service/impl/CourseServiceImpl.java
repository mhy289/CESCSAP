package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SCMapper scMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

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
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherMapper.selectTeacherById(teacherId);
        if (teacher == null) {
            throw new BusinessException(ExceptionType.TEACHER_NOT_FOUND, "教师不存在");
        }
        course.setTeacherName(teacher.getName());
        return courseMapper.addCourse(course);
    }



    @Override
    public Integer deleteCourse(Long id) {
        return courseMapper.deleteCourse(id);
    }

    @Override
    public PageItem<Course> queryConditionPage(Course course, Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Course> orderList = courseMapper.selectByCondition(course);
        log.info("orderList is {}" , orderList);
        Page<Course> info = (Page<Course>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public Integer addStudents(Long courseId, Long[] studentIds) {
        //将学生批量添加到课程，学生不能重复选择课程
        for (Long studentId : studentIds) {
            StudentCourse sc = new StudentCourse();
            sc.setCourseId(courseId);
            sc.setStudentId(studentId);
            Course course = courseMapper.getCourseById(courseId);
            sc.setCourseName(course.getCourseName());
            sc.setStudentName(studentMapper.getStudentById(studentId).getName());
            sc.setTeacherId(course.getTeacherId());
            sc.setTeacherName(course.getTeacherName());
            sc.setEvaluateStatus(-1);
            //查重
            Integer count = scMapper.countByStudentAndCourse(studentId, courseId);
            if (count == 0) {
                Integer i = scMapper.addStudentCourse(sc);
            }
        }
        return studentIds.length;
    }

    @Override
    public PageItem<Course> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Course> orderList = courseMapper.getCourses();
        Page<Course> info = (Page<Course>) orderList;
        Long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public List<Student> getStudentsByCourse(Long courseId) {
        return initStudent(courseId);
    }

    @Override
    public List<Student> getNoStudentsByCourse(Long courseId) {
        List<Student> students = initStudent(courseId);
        List<Student> studentList = studentMapper.getStudents();
        studentList.removeAll(students);
        return studentList;
    }

    @Override
    public Integer deleteStudentsByCourse(Long courseId, Long[] studentIds) {
        for(Long studentId : studentIds){
            StudentCourse sc = new StudentCourse();
            sc.setCourseId(courseId);
            sc.setStudentId(studentId);
            //查重
            Integer count = scMapper.countByStudentAndCourse(studentId, courseId);
            if(count>0){
                scMapper.deleteStudentsByCourse(sc);
            } else {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public Integer deleteStudentByc(Long courseId, Long studentId) {
        StudentCourse sc =new StudentCourse();
        sc.setCourseId(courseId);
        sc.setStudentId(studentId);
        return scMapper.deleteStudentsByCourse(sc);
    }

    private List<Student> initStudent(Long courseId) {
        List<StudentCourse> scs = scMapper.getStudentsByCourse(courseId);
        List<Student> students =new ArrayList<>();
        for(StudentCourse sc : scs){
            Long studentId = sc.getStudentId();
            Student student = studentMapper.getStudentById(studentId);
            students.add(student);
        }
        return students;
    }
}
