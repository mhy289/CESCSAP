package com.mhy.cescsap;

import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.NoticeService;
import com.mhy.cescsap.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CescsapApplicationTests {

    @Autowired
    TeacherService teacherService;

    @Autowired
    NoticeService noticeService;

    @Test
    void contextLoads() {
        List<Teacher> teachers = teacherService.getTeachers();
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    @Test
    void Noticelodads() {
        System.out.println(noticeService.getNoticeList());
    }

}
