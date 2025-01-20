package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseHistory {
    //历史成绩id
    private Long schId;
    // 学生课程id
    private Long scId;
    // 历史成绩
    private Double score;
    // 考试日期
    private Date examDate;
}
