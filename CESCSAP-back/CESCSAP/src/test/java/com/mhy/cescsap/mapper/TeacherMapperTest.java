package com.mhy.cescsap.mapper;

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

    @Test
    void selectTeacherById() {
    }

    @Test
    void insertTeacher() {
        Teacher t = new Teacher();
        t.setName("负");
        t.setGender("男");
        t.setDepartment("计算机学院");
        t.setBirthDate(new Date());
        t.setContact("13688889999");
        int result = mapper.insertTeacher(t);
        System.out.println(result);
    }
}