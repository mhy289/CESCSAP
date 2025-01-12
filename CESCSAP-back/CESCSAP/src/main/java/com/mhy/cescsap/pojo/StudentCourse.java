package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    //学生课程id
    private Long studentCourseId;
    //学生id
    private Long studentId;
    //课程id
    private Long courseId;
    //学生课程成绩
    private Double score;
}
