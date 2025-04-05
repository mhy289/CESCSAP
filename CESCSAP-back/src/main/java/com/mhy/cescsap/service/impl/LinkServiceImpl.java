package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.LinkMapper;
import com.mhy.cescsap.pojo.Link;
import com.mhy.cescsap.pojo.PageItem;
import com.mhy.cescsap.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkMapper linkMapper;

    @Override
    public List<Link> getLinks() {
        return linkMapper.getLinkList();
    }

    @Override
    public Link getLinkById(Long id) {
        return linkMapper.getLinkById(id);
    }

    @Override
    public Integer addLink(Link link) {
        return linkMapper.addLink(link);
    }

    @Override
    public Integer updateLink(Link link) {
        return linkMapper.updateLink(link);
    }

    @Override
    public Integer deleteLink(Long id) {
        return linkMapper.deleteLink(id);
    }

    @Override
    public PageItem<Link> getLinksByPage(Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        List<Link> links = linkMapper.getLinkList();
        // 获取总记录数
        Page<Link> pages = (Page<Link>) links;
        return new PageItem<>(pages.getTotal(), links);
    }
}
