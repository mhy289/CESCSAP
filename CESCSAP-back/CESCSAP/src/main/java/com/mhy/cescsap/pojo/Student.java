package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    //学号
    private Long studentId;
    //名字
    private String name;
    //性别
    private String gender;
    //出生日期
    private String birthDate;
    //专业
    private String major;
    //班级id
    private Long classId;
    //联系方式
    private String contact;
    //绩点
    private Double gpa;
    //预警信息列表
    private List<Warning> warnings;
    //自评结果
    private String selfEvaluation;
    //教师评价结果
    private String teacherEvaluation;
    //同学评价结果
    private String classmateEvaluation;
    //登陆状态
    private boolean loginStatus;
}
