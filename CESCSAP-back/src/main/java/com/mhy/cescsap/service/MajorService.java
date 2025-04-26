package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Major;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MajorService {

    List<Major> getMajors();


    Integer addMajor(Major major);

    Integer deleteMajor(Long id);

    Integer updateMajor(Major major);
}
