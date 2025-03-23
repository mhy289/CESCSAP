package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Partition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SectionMapper {
    //查询所有分区
    @Select("select * from section")
    List<Partition> getAllSections();
}
