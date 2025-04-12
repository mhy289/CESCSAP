package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    //学生课程id
    private Long scId;
    //学生id
    private Long studentId;
    //学生姓名
    private String studentName;
    //课程id
    private Long courseId;
    //课程名称
    private String courseName;
    //教师id
    private Long teacherId;
    //教师姓名
    private String teacherName;
    //学生课程成绩
    private Double score;
    //绩点
    private Double gpa;
    // 考试日期
    private Date examDate;

    private Student student;

    private Course course;

    private  Integer evaluateStatus;

    public void setGpa() {
        if(this.score<60){
            this.gpa = 0.0;
        } else{
            this.gpa = (this.score - 50) / 10;
        }
    }
}
