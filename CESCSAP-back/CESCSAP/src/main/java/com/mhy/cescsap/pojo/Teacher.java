package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    //教师id
    private Long teacherId;
    //姓名
    private String name;
    //性别
    private String gender;
    //所属院系
    private String department;
    //出生日期
    private String birthDate;
    //联系方式
    private String contact;
    //所授课程列表
    private List<Course> courses;
    //发布的预警信息列表
    private List<Warning> warnings;
    //收到的学生响应回信列表
    private List<Response> responses;
    //给出的学生评价列表
    private List<Comment> comments;
    //登陆状态
    private boolean loginStatus;
}
