package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.NoticeMapper;
import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Notice getNotice(Long id) {
        return noticeMapper.getNoticeById(id);
    }

    @Override
    public List<Notice> getNoticeList() {
        return noticeMapper.getAllNotices();
    }

    @Override
    public Integer updateNotice(Notice notice) {
        notice.setCreateTime(new Date());
        return noticeMapper.updateNotice(notice);
    }

    @Override
    public Integer deleteNotice(Long id) {
        return noticeMapper.deleteNotice(id);
    }

    @Override
    public Integer addNotice(Notice notice) {
        return noticeMapper.addNotice(notice);
    }

    @Override
    public List<Notice> getNoticeByContent(String content) {
        return noticeMapper.getNoticesByContent(content);
    }

    @Override
    public List<Notice> getNoticeByTitle(String title) {
        return noticeMapper.getNoticesByTitle(title);
    }

    @Override
    public PageItem<Notice> getNoticeByTitlePage(String title, Integer pageNum, Integer pageSize) {
        PageHelper.startPage( pageNum, pageSize);
        List<Notice> notices = noticeMapper.getNoticesByTitle(title);
        log.debug("notices is {}", notices);
        Page<Notice> page = (Page<Notice>) notices;
        return  new PageItem<>(page.getTotal(),notices);
    }

    @Override
    public List<Notice> getNoticeByPublisher(String publisher) {
        return noticeMapper.getNoticesByPublisher(publisher);
    }

    @Override
    public PageItem<Notice> getAllNotices(Integer page, Integer size) {
        if(page == null || size==null){
            throw new RuntimeException();
        }
        log.debug("page is {}",page);
        log.debug("size is {}",size);
        log.debug("notices is {}", noticeMapper.getAllNotices());
        //分页查询公告
        PageHelper.startPage(page, size);
        List<Notice> notices = noticeMapper.getAllNotices();
        log.debug("notices is {}", notices);
        Page<Notice> pages = (Page<Notice>) notices;
        return new PageItem<>(pages.getTotal(), notices);
    }
}
