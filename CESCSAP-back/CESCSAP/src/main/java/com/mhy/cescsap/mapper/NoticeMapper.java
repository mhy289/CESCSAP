package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    //查询一条公告
    @Select("select * from notice where notice_id = #{noticeId}")
    Notice getNoticeById(Long noticeId);
    //查询所有公告
    @Select("select * from notice")
    List<Notice> getAllNotices();
    //添加公告
    @Insert("insert into notice(notice_id, title, contact, publisher, create_time) values (#{noticeId}, #{title}, #{contact},#{publisher}, #{createTime})")
    Integer addNotice(Notice notice);
    //修改公告
    @Update("update notice set title = #{title}, contact = #{contact}, publisher = #{publisher}, create_time = #{createTime} where notice_id = #{noticeId}")
    Integer updateNotice(Notice notice);
    //删除公告
    @Delete("delete from notice where notice_id = #{noticeId}")
    Integer deleteNotice(Long noticeId);
}
