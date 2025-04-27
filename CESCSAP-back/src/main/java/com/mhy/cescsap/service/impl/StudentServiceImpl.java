package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.*;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.service.StudentService;
import com.mhy.cescsap.utils.SchoolNumberGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mhy.cescsap.utils.AccountGeneratorUtils.generateAccount;
import static com.mhy.cescsap.utils.NameGeneratorUtils.getRandomName;

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

    @Autowired
    MajorMapper majorMapper;

    @Autowired
    DepartmentMapper departmentMapper;

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
        if(student.getName() == null || student.getName().isEmpty()) {
            //throw new BusinessException(ExceptionType.NAME_NOT_FOUND, "姓名不能为空");
            String name = getRandomName();
            //查重
            while (studentMapper.existsByName(name)) {
                name = getRandomName();
            }
            student.setName(name);
        }
        //名字查重
        if (studentMapper.existsByName(student.getName())) {
            throw new BusinessException(ExceptionType.NAME_DUPLICATE, "姓名重复");
        }
        List<Student> students = studentMapper.getStudents();
        Long classId = student.getClassId();
        Map<String, Long> map = new HashMap<>();
        String major = student.getMajor();
        Major m = majorMapper.getMajor(major);
        if (m == null || m.getMajorId() == null) {
            throw new BusinessException(ExceptionType.MAJOR_NOT_FOUND, "专业不存在");
        }
        Long departmentId = m.getDepartmentId();
        Department department = departmentMapper.getDepartmentById(departmentId);
        if (department == null || department.getDepartmentId() == null) {
            throw new BusinessException(ExceptionType.DEPARTMENT_NOT_FOUND, "学院不存在");
        }
        student.setDepartmentId(departmentId);
        student.setDepartment(department.getDepartmentName());
        if (classId == null) {
            throw new BusinessException(ExceptionType.CLASS_NOT_FOUND, "班级不能为空");
        }
        if (classMapper.getClassByClassId(classId) == null) {
            throw new BusinessException(ExceptionType.CLASS_NOT_FOUND, "班级不存在");
        }
        long account;
        do {
            account = generateAccount();
        } while (studentMapper.existsByAccount(account)); // 查重
        student.setAccount(account);
        student.setPassword("123456");
        student.setContact("777");
        student.setRole(2L);
        student.setEvaluateStatus(0);
        student.setBirthDate(new Date(20000));
        student.setLoginStatus(0);
        student.setEnrollDate(new Date());
        student.setGpa(0.0);
        student.setClassName(classMapper.getClassByClassId(classId).getClassName());
        log.debug("stu is {}", student);
        return studentMapper.addStudent(student);
    }

    @Override
    public Integer deleteStudent(Long id) {
        return studentMapper.deleteStudent(id);
    }

    @Override
    public Integer updateStudent(Student student) {
        if(student.getName() == null || student.getName().isEmpty()) {
            //throw new BusinessException(ExceptionType.NAME_NOT_FOUND, "姓名不能为空");
            String name = getRandomName();
            //查重
            while (studentMapper.existsByName(name)) {
                name = getRandomName();
            }
            student.setName(name);
        }
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
        List<TeacherCourse> tclist = new ArrayList<>();
        for (StudentCourse sc : studentCourses) {
            Long courseId = sc.getCourseId();
            Course course = courseMapper.getCourseById(courseId);
            TeacherCourse tc = new TeacherCourse(course.getTeacherName(), course.getCourseName());
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
        int sum = 0;
        for (Student student : students) {

            if (student.getAccount() == null) {
                long account;
                do {
                    account = generateAccount();
                } while (studentMapper.existsByAccount(account)); // 查重
                student.setAccount(account);
                Integer i = studentMapper.addStudentForAccount(student);
            }
            String major = student.getMajor();
            if(student.getDepartment()== null|| student.getDepartmentId() == null){
                Major m = majorMapper.getMajor(major);
                student.setDepartmentId(m.getDepartmentId());
                Department department = departmentMapper.getDepartmentById(m.getDepartmentId());
                student.setDepartment(department.getDepartmentName());
                studentMapper.updateStudentDepartment(student);
            }
            Long classId = student.getClassId();
            Integer i = classMapper.checkMajor(major);
            student.setPassword("123456");
            student.setEnrollDate(new Date());
            //更新入学日期
            Integer z = studentMapper.updateEnrollDate(student);
            if (i == null || i <= 0) {
                Class clazz = classMapper.getClassByClassId(classId);
                student.setMajor(clazz.getMajor());
                student.setClassName(clazz.getClassName());
                Integer i1 = studentMapper.updateStudent(student);
                sum++;
            }
        }
        return sum;
    }

    @Override
    public Integer addStudentNumber(Student student) {
        String s = SchoolNumberGeneratorUtils.generateStudentNumber(student, classMapper.getClassByClassId(student.getClassId()));
        return studentMapper.updateStudentNumber(s, student.getStudentId());
    }

    @Override
    public Integer check() {
        List<Student> students = studentMapper.getStudents();
        for (Student student : students) {
            if (student.getDepartment() == null || student.getDepartmentId() == null) {
                String major = student.getMajor();
                Major m = majorMapper.getMajor(major);
                Long departmentId = m.getDepartmentId();
                Department d = departmentMapper.getDepartmentById(departmentId);
                student.setDepartment(d.getDepartmentName());
                student.setDepartmentId(departmentId);
                Integer i = studentMapper.updateStudentDepartment(student);
                if (i == null || i <= 0) {
                    return 0;
                }
            }
            if (student.getStudentNumber() == null) {
                String s = SchoolNumberGeneratorUtils.generateStudentNumber(student, classMapper.getClassByClassId(student.getClassId()));
                student.setStudentNumber(s);
                Integer i = studentMapper.updateStudentNumber(s, student.getStudentId());
                if (i == null || i <= 0) {
                    return 0;
                }
            }
        }
        return 1;
    }
}
