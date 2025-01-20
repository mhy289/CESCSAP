package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Notice;

import java.util.List;

public interface NoticeService {
    //查询公告
    Notice getNotice(Long id);
    //查询所有公告
    List<Notice> getNoticeList();
    //修改公告
    Integer updateNotice(Notice notice);
    //删除公告
    Integer deleteNotice(Long id);
    //添加公告
    Integer addNotice(Notice notice);
}
