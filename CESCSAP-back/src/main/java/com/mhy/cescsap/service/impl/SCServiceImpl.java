package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.CourseMapper;
import com.mhy.cescsap.mapper.SCMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.SCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SCServiceImpl implements SCService {

    @Autowired
    SCMapper scMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;


    @Override
    public List<StudentCourse> getSC(Long scId) {
        return scMapper.getStudents(scId);
    }

    @Override
    public Integer addSC(StudentCourse studentCourse) {
        return scMapper.addStudentCourse(studentCourse);
    }

    @Override
    public PageItem<StudentCourse> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<StudentCourse> orderList = scMapper.getStudents(Long.valueOf(current));
        long total = ((Page<StudentCourse>) orderList).getTotal();
        return new PageItem<>(total, orderList);
    }

    @Override
    public PageItem<StudentCourseHistory> queryConditionPage(Student student, Integer current, Integer size) {
        // 在查询前启动分页，确保分页生效
        PageHelper.startPage(current, size);
        log.info("Student is {}", student);
        // 执行分页查询，此时返回的List实际上是Page类型
        List<StudentCourse> students = scMapper.selectByCondition2(student.getStudentId());
        log.debug("Student is {}", students);

        // 将结果强制转换为Page以获取分页信息
        Page<StudentCourse> page = (Page<StudentCourse>) students;

        List<StudentCourseHistory> schs = new ArrayList<>();
        for (StudentCourse studentCourse : students) {
            Course course = courseMapper.getCourseById(studentCourse.getCourseId());
            StudentCourseHistory sch = new StudentCourseHistory();
            sch.setCourseName(course.getCourseName());
            sch.setTeacherName(course.getTeacherName());
            sch.setScore(studentCourse.getScore());
            sch.setGpa(studentCourse.getGpa());
            sch.setCredit(course.getCredit());
            sch.setExamDate(studentCourse.getExamDate());
            schs.add(sch);
            log.debug("sch is {}", sch);
        }
        log.debug("schs is {}", schs);
        log.debug("pahe");
        // 使用分页数据构造返回结果
        return new PageItem<>(page.getTotal(), schs);
    }

    @Override
    public Object queryStudentCourse(Student student) {
        return null;
    }

    @Override
    public Integer refreshGPA(Student student) {
        String name = student.getName();
        Student student1 = studentMapper.getStudentByName(name);
        Long studentId = student1.getStudentId();
        List<StudentCourse> studentCourses = scMapper.selectByCondition2(studentId);
        Double allGpa = 0.0;
        int cnt = 0;
        for (StudentCourse sc : studentCourses) {
            Double gpa = sc.getGpa();
            cnt++;
            allGpa += gpa;
        }
        if (cnt > 0) {
            Double avgGpa = allGpa / cnt;
            student.setGpa(avgGpa);
            return studentMapper.updateStudent(student);
        } else {
            log.info("Not found student {} in the database", name);
            student.setGpa(0.0);
            return studentMapper.updateStudent(student);
        }
    }

    @Override
    public List<StudentCourse> getAllSCByStudentId(Long studentId) {
        return scMapper.selectByCondition2(studentId);
    }

    @Override
    public Integer getAllSC() {
        log.debug("--------------------------------");
        List<Student> students;
        try {
            // 获取所有学生列表
            students = studentMapper.getStudents();
        } catch (Exception e) {
            log.error("获取学生列表时发生异常", e);
            return 0;
        }
        if (students == null || students.isEmpty()) {
            log.info("没有找到学生信息");
            return 0;
        }

        int successUpdateCount = 0;
        for (Student student : students) {
            try {
                Long studentId = student.getStudentId();
                if (studentId == null) {
                    log.warn("学生ID为空，跳过处理");
                    continue;
                }

                List<StudentCourse> studentCourses;
                try {
                    // 获取学生的选课信息
                    studentCourses = scMapper.selectByCondition2(studentId);
                } catch (Exception e) {
                    log.error("查询学生课程失败，学生ID: {}", studentId, e);
                    continue;
                }

                // 处理无课程情况
                if (studentCourses == null || studentCourses.isEmpty()) {
                    student.setGpa(0.0);
                } else {
                    // 计算平均绩点
                    double totalGpa = 0.0;
                    int validCourses = 0;
                    for (StudentCourse course : studentCourses) {
                        if (course.getGpa() == null) {
                            log.warn("学生ID: {} 的课程 {} GPA为空", studentId, course.getCourseId());
                            continue;
                        }
                        totalGpa += course.getGpa();
                        validCourses++;
                    }

                    if (validCourses == 0) {
                        student.setGpa(0.0);
                    } else {
                        student.setGpa(totalGpa / validCourses);
                    }
                }

                // 更新学生信息
                int result = studentMapper.updateStudent(student);
                log.debug("res is {}", result);
                if (result > 0) {
                    successUpdateCount++;
                }
            } catch (Exception e) {
                log.error("处理学生信息异常，学生ID: {}", student.getStudentId(), e);
                throw new BusinessException(ExceptionType.STU_ERR,"学生异常");
            }
        }
        return successUpdateCount;
    }
}
