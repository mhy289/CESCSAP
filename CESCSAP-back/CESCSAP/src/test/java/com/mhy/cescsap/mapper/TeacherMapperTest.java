package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Admin;
import com.mhy.cescsap.pojo.Course;
import com.mhy.cescsap.pojo.StudentCourse;
import com.mhy.cescsap.pojo.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherMapperTest {

    @Autowired
    TeacherMapper mapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SCMapper scMapper;

    @Test
    void selectTeacherById() {
    }

    @Test
    void insertTeacher() {
        Teacher t = new Teacher();
        t.setName("负0");
        t.setGender("男s");
        t.setDepartment("s计算机学院");
        t.setBirthDate(new Date());
        t.setContact("136288889999");
        int result = mapper.insertTeacher(t);
        System.out.println(result);
    }

    @Test
    void createAdmin() {
        Admin admin = new Admin();
        admin.setName("admin2");
        admin.setPassword("123456");
        admin.setContact("777");
        admin.setLoginStatus(0);
        admin.setRole(0L);
        int result = adminMapper.createAdmin(admin);
        System.out.println(result);
    }

    @Test
    void addCourse() {
        Course course = new Course();
        course.setCourseName("java");
        course.setTeacherId(1L);
        course.setDescription("计算机学院");
        course.setCredit(2.0);
        courseMapper.addCourse(course);
        System.out.println("add course success");
    }

    @Test
    void addSC(){
        StudentCourse sc = new StudentCourse();
        sc.setStudentId(1L);
        sc.setCourseId(1L);
        sc.setScore(100.0);
        sc.setExamDate(new Date());
        scMapper.addStudentCourse(sc);
        System.out.println("add sc success");
    }
}