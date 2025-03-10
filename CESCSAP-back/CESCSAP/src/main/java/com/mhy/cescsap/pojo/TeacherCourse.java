package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCourse {
    // 教师课程id
    private Long teacherCourseId;
    // 教师id
    private Long teacherId;
    private String teacherName;
    // 课程id
    private Long courseId;
    private String courseName;

    public TeacherCourse(String teacherName, String courseName) {
        this.teacherName=teacherName;
        this.courseName=courseName;
    }
}
