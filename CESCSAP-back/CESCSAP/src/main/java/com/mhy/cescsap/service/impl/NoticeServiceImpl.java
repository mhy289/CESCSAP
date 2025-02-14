package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.NoticeMapper;
import com.mhy.cescsap.pojo.Notice;
import com.mhy.cescsap.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
