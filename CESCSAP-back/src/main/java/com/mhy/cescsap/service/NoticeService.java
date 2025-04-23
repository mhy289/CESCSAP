package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.pojo.PageItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
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

    //根据内容查询公告
    List<Notice> getNoticeByContent(String content);

    //根据标题查询公告
    List<Notice> getNoticeByTitle(String title);

    //根据标题 分页查询公告
    PageItem<Notice> getNoticeByTitlePage(String title, Integer pageNum, Integer pageSize);

    //根据创建人查询公告
    List<Notice> getNoticeByPublisher(String publisher);

    PageItem<Notice> getAllNotices(Integer page, Integer size);
}
