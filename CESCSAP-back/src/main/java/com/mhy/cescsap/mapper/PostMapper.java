package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PostMapper {
    //查看该分区下所有帖子
    @Select("select * from post where partition_id = #{partitionId}")
    List<Post> findPostByPartitionId(Long partitionId);

    //发帖
    @Insert("insert into post (partition_id,post_title, post_content, post_author, create_time,reply_count,view_count) values ( #{partitionId},#{postTitle}, #{postContent}, #{postAuthor}, #{createTime}, #{replyCount}, #{viewCount})")
    Integer addPost(Post p);

    //删帖
    @Insert("delete from post where post_id = #{postId}")
    Integer deletePost(Long postId);

    //获取帖子
    @Select("select * from post where post_id = #{postId}")
    Post getPost(Long postId);

    //增加浏览量
    @Update("update post set view_count = view_count + 1 where post_id = #{postId}")
    Integer incrementViewCount(Long postId);

    //增加回复量
    @Update("update post set reply_count = reply_count + 1 where post_id = #{postId}")
    Integer incrementReplyCount(Long postId);
}
