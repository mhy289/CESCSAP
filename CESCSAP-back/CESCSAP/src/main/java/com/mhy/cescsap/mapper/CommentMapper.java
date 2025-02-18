package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    //查询评价
    //查询单个评价
    @Select("select * from comment where comment_id = #{comment_id}")
    Comment getCommentById(Long commentId);
    //查询所有评价
    @Select("select * from comment")
    List<Comment> getAllComments();
    //删除评价
    @Delete("delete from comment where comment_id = #{commentId}")
    Integer deleteComment(Long commentId);
    //添加评价
    @Insert("insert into comment(content, course_id, student_id,teacher_id) values(#{content}, #{courseId}, #{studentId},#{teacherId})")
    Integer addComment(Comment comment);
    //修改评价
    @Update("update comment set content = #{content}, course_id = #{courseId}, student_id = #{studentId}, teacher_id = #{teacherId} where comment_id = #{commentId}")
    Integer updateComment(Comment comment);
}
