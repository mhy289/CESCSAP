package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Partition;
import com.mhy.cescsap.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SectionMapper {
    //查询所有分区
    @Select("select * from section")
    List<Partition> getAllSections();

    //查询分区下所有帖子
    @Select("select * from section where section_id = #{sectionId}")
    List<Post> findAllPostBySectionId(Long sectionId);
}
