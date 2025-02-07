package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    //课程id
    private Long courseId;
    //课程名称
    private String courseName;
    //课程教师id
    private Long teacherId;
    //教师名字
    private String teacherName;
    //课程描述
    private String description;
    // 课程学分
    private Double credit;
}
