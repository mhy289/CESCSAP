package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User{
    //教师id
    private Long teacherId;
//    //姓名
//    private String name;
//    //密码
//    private String password;
    //性别
    private String gender;
    //所属院系
    private String department;
    //出生日期
    private Date birthDate;
    //联系方式
    private String contact;

    private List<Course> courses;
    private List<TeacherEvaluationStats> teacherEvaluationStats;
    //登陆状态
    private Integer loginStatus;
}
