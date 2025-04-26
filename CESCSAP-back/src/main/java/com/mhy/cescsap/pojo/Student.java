package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User{
    //学生id
    private Long studentId;
    //学号
    private String studentNumber;
//    //名字
//    private String name;
//    //密码
//    private String password;
    //性别
    private String gender;
    private Long departmentId;
    //所属院系
    private String department;
    //出生日期
    private Date birthDate;
    //专业
    private String major;
    //班级id
    private Long classId;
    //班级名字
    private String className;
    //联系方式
    private String contact;
    //绩点
    private Double gpa;
    //入学日期
    private Date enrollDate;
    //评价状态
    private Integer evaluateStatus;
    //登陆状态
    private Integer loginStatus;
}
