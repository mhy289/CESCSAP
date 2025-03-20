package com.mhy.cescsap.service;


import com.mhy.cescsap.pojo.Comment;

public interface CommentService {
    //添加评价
    Integer addComment(Integer studentId, Integer courseId, String content);
    //查询单个课程的评价
    Comment getCommentCount(Integer courseId);
    //查询某一课程的某一位学生的评价
    Comment getCommentByStudentIdAndCourseId(Integer studentId, Integer courseId);
    //查询某一课程的某一位教师的评价
    Comment getCommentByTeacherIdAndCourseId(Integer teacherId, Integer courseId);
    //查询某一课程的某一位学生的全部评价
    Comment getAllCommentsByStudentIdAndCourseId(Integer studentId, Integer courseId);
    //查询某一课程的某一位教师的全部评价
    Comment getAllCommentsByTeacherIdAndCourseId(Integer teacherId, Integer courseId);
    //查询某一课程的全部评价
    Comment getAllCommentsByCourseId(Integer courseId);
    // 删除某一课程的某一位学生的全部评价
    Integer deleteAllCommentsByStudentIdAndCourseId(Integer studentId, Integer courseId);

}
