package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.pojo.Comment;
import com.mhy.cescsap.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Override
    public Integer addComment(Integer studentId, Integer courseId, String content) {
        return null;
    }

    @Override
    public Comment getCommentCount(Integer courseId) {
        return null;
    }

    @Override
    public Comment getCommentByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return null;
    }

    @Override
    public Comment getCommentByTeacherIdAndCourseId(Integer teacherId, Integer courseId) {
        return null;
    }

    @Override
    public Comment getAllCommentsByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return null;
    }

    @Override
    public Comment getAllCommentsByTeacherIdAndCourseId(Integer teacherId, Integer courseId) {
        return null;
    }

    @Override
    public Comment getAllCommentsByCourseId(Integer courseId) {
        return null;
    }

    @Override
    public Integer deleteAllCommentsByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return null;
    }
}
