package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    //班级id
    private Long classId;
    //班级名称
    private String className;
    //所属专业
    private String major;
    //班级列表
    private List<Student> students;
}
