package com.mhy.cescsap;

import com.mhy.cescsap.pojo.EvaluationDimension;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.service.EvaluationDimensionService;
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

    @Autowired
    EvaluationDimensionService evaluationDimensionService;

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


    @Test
    void testDimensions() {
        EvaluationDimension evaluationDimension=new EvaluationDimension();
        evaluationDimension.setDimensionName("课程表现");
        evaluationDimension.setDescription("是否认真上课");
        evaluationDimensionService.getEvaluationDimension(evaluationDimension);
    }
}
