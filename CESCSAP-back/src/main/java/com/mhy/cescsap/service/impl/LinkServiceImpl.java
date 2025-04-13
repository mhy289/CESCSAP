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

import static com.mhy.cescsap.utils.RegexValidatorUtils.isValidDomain;

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
        //验证链接合法性
        String url = link.getUrl();
        //使用正则表达式工具类验证链接合法性
        if(isValidDomain(url)){
            //判断是否已经存在该链接
            Link link1 = linkMapper.getLinkById(link.getLinkId());
            if(link1 == null){
                //不存在该链接，添加
                return linkMapper.addLink(link);
            }else {
                //存在该链接，更新
                link.setLinkId(link1.getLinkId());
                return linkMapper.updateLink(link);
            }
        }else {
            return 0;
        }

    }

    @Override
    public Integer updateLink(Link link) {
        //验证链接合法性
        String url = link.getUrl();
        //使用正则表达式工具类验证链接合法性
        if(isValidDomain(url)){
            //判断是否已经存在该链接
            Link link1 = linkMapper.getLinkById(link.getLinkId());
            if(link1 == null){
                //不存在该链接，添加
                return linkMapper.addLink(link);
            }else {
                //存在该链接，更新
                link.setLinkId(link1.getLinkId());
                return linkMapper.updateLink(link);
            }
        }else {
            return 0;
        }
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
