package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.URL;

import java.util.List;

public interface URLService {
    //查看所有友情链接
    List<URL> getURLs();

    //新增友情链接
    Integer addURL(URL url);

    //修改友情链接
    Integer updateURL(URL url);

    //删除友情链接
    Integer deleteURL(Long id);
}
