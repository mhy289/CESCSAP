package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Link;
import com.mhy.cescsap.pojo.PageItem;

import java.util.List;

public interface LinkService {
    //查询所有链接
    List<Link> getLinks();

    //根据id查询链接
    Link getLinkById(Long id);

    //添加链接
    Integer addLink(Link link);

    //修改链接
    Integer updateLink(Link link);

    //删除链接
    Integer deleteLink(Long id);

    PageItem<Link> getLinksByPage(Integer page, Integer size);
}
