package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warning {
    //预警id
    private Long warningId;
    //学生id
    private Long studentId;
    //课程id
    private Long courseId;
    //分布教师Id
    private Long teacherId;
    //预警内容
    private String warningContent;
    //发送日期
    private Date sendDate;
    //学生响应
    private String studentResponse;
}
