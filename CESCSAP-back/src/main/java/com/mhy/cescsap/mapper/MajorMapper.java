package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MajorMapper {

    @Select("select * from major")
    List<Major> getAllMajor();

    @Select("select * from major where major_id = #{majorId}")
    Major getMajorById(Long majorId);

    @Select("select * from major where major_name = #{majorName}")
    Major getMajor(String majorName);
}
