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
    //平时成绩
    private Double usualScore;
    //考试成绩
    private Double examScore;
    //平时成绩占比
    private Double usualRate;
    //考试成绩占比
    private Double examRate;
    //学生课程最终成绩
    private Double score;
    //绩点
    private Double gpa;
    // 考试日期
    private Date examDate;

    private Student student;

    private Course course;
    // 评价状态 0 未评价 1 已评价 -1未批改
    private  Integer evaluateStatus;

    public void setGpa() {
        if(this.score<60){
            this.gpa = 0.0;
        } else{
            this.gpa = (this.score - 50) / 10;
        }
    }
}
