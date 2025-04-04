package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.URLMapper;
import com.mhy.cescsap.pojo.URL;
import com.mhy.cescsap.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class URLServiceImpl implements URLService {

    @Autowired
    URLMapper urlMapper;

    @Override
    public List<URL> getURLs() {
        return urlMapper.getAllURLs();
    }

    @Override
    public Integer addURL(URL url) {
        return urlMapper.insertURL(url);
    }

    @Override
    public Integer updateURL(URL url) {
        return urlMapper.updateURL(url);
    }

    @Override
    public Integer deleteURL(Long id) {
        return urlMapper.deleteURL(id);
    }
}
