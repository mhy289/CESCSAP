package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReplyMapper {

    //查询所有回复
    @Select("select * from reply")
    List<Reply> getReplyList();

    //修改回复
    @Update("update reply set contact = #{contact} where reply_id = #{replyId}")
    Integer updateReply(Reply reply);

    @Update("update reply set contact = #{contact} where user_id = #{userId} and reply_id = #{replyId} and partition_id = #{partitionId} and post_id = #{postId}")
    Integer updateReplyByUser(Reply reply);

    //删除回复
    @Update("update reply set contact = #{contact} where reply_id = #{replyId}")
    Integer deleteReply(Reply reply);

    @Update("update reply set contact = #{contact} where user_id = #{userId} and reply_id = #{replyId} and partition_id = #{partitionId} and post_id = #{postId}")
    Integer deleteReplyByUser(Reply reply);

    //添加回复
    @Insert("insert into reply(reply_id, partition_id, post_id, user_id, contact, reply_time) values (#{replyId}, #{partitionId}, #{postId}, #{userId},#{contact},#{replyTime})")
    Integer addReply(Reply reply);

    @Select("select * from reply where partition_id = #{partitionId} and post_id = #{postId}")
    List<Reply> getReplyContent(Reply reply);
}
