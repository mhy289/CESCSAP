package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//评价
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    // 评价id
    private Long commentId;
    // 学生id
    private Long studentId;
    // 课程id
    private Long courseId;
    // 教师id
    private Long teacherId;
    //评价内容
    private String content;
}
