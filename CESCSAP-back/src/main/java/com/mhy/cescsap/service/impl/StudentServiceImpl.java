package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.ClassMapper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mhy.cescsap.utils.AccountGeneratorUtils.generateAccount;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SCMapper scMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ClassMapper classMapper;

    @Override
    public List<Student> getStudents() {
        return studentMapper.getStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public Integer addStudent(Student student) {
        Long classId = student.getClassId();
        if(classId==null) {
            throw new BusinessException(ExceptionType.CLASS_NOT_FOUND,"班级不能为空");
        }
        if(classMapper.getClassByClassId(classId)==null){
            throw new BusinessException(ExceptionType.CLASS_NOT_FOUND,"班级不存在");
        }
        long account;
        do {
            account =generateAccount();
        } while (studentMapper.existsByAccount(account)); // 查重
        student.setAccount(account);
        student.setPassword("123456");
        student.setContact("777");
        student.setRole(2L);
        student.setEvaluateStatus(0);
        student.setBirthDate(new Date());
        student.setClassName(classMapper.getClassByClassId(classId).getClassName());
        log.debug("stu is {}",student);
        return studentMapper.addStudent(student);
    }

    @Override
    public Integer deleteStudent(Long id) {
        return studentMapper.deleteStudent(id);
    }

    @Override
    public Integer updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public PageItem<Student> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Student> orderList = studentMapper.getStudents();
        long total = ((Page<Student>) orderList).getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public PageItem<Student> queryConditionPage(Student student, Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Student> orderList = studentMapper.selectByCondition2(student);
        Page<Student> info = (Page<Student>) orderList;
        long total = info.getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentMapper.getStudentByName(name);
    }

    @Override
    public List<TeacherCourse> getFilterOptions(Student student) {
        String name = student.getName();
        Student student1 = studentMapper.selectStudentByName(name);
        Long studentId = student1.getStudentId();
        List<StudentCourse> studentCourses = scMapper.selectByCondition2(studentId);
        List<TeacherCourse> tclist=new ArrayList<>();
        for(StudentCourse sc : studentCourses){
            Long courseId = sc.getCourseId();
            Course course = courseMapper.getCourseById(courseId);
            TeacherCourse tc=new TeacherCourse(course.getTeacherName(),course.getCourseName());
            tclist.add(tc);
        }
        return tclist;
    }

    @Override
    public Integer updateCommentStatus() {
        return studentMapper.updateEvaluateStatus(1);
    }

    @Override
    public Integer updateCommentStatus(Student student) {
        return studentMapper.updateEvaluateStatus1(student);
    }

    @Override
    public PageItem<Student> getAllPage(Integer current, Integer size) {
        return null;
    }

    @Override
    public Integer updateMajor() {
        List<Student> students = studentMapper.getStudents();
        int sum=0;
        for(Student student : students){
            if(student.getAccount()==null){
                long account;
                do {
                    account =generateAccount();
                } while (studentMapper.existsByAccount(account)); // 查重
                student.setAccount(account);
                Integer i = studentMapper.addStudentForAccount(student);
            }
            String major = student.getMajor();
            Long classId = student.getClassId();
            Integer i = classMapper.checkMajor(major);
            student.setPassword("123456");
            if(i == null || i<=0){
                Class clazz = classMapper.getClassByClassId(classId);
                student.setMajor(clazz.getMajor());
                Integer i1 = studentMapper.updateStudent(student);
                sum++;
            }
        }
        return sum;
    }
}
