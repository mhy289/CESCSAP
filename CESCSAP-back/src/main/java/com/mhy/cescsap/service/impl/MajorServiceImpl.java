package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.MajorMapper;
import com.mhy.cescsap.pojo.Major;
import com.mhy.cescsap.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorMapper majorMapper;

    @Override
    public List<Major> getMajors() {
        return majorMapper.getAllMajor();
    }

    @Override
    public Integer addMajor(Major major) {
        return majorMapper.addMajor(major);
    }

    @Override
    public Integer deleteMajor(Long id) {
        return majorMapper.deleteMajor(id);
    }

    @Override
    public Integer updateMajor(Major major) {
        return majorMapper.updateMajor(major);
    }


}
