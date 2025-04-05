package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Link;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface LinkMapper {
    @Select("select * from link")
    List<Link> getLinkList();

    @Select("select * from link where link_id = #{id}")
    Link getLinkById(Long id);

    @Insert("insert into link(name, url, description) VALUES (#{name}, #{url}, #{description})")
    Integer addLink(Link link);

    @Update("update link set name = #{name}, url = #{url}, description = #{description} where link_id = #{linkId}")
    Integer updateLink(Link link);

    @Delete("delete from link where link_id = #{linkId}")
    Integer deleteLink(Long linkId);
}
