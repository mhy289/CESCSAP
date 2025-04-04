package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.URL;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface URLMapper {
    //获取友情链接
    @Select("select * from url")
    List<URL> getAllURLs();
    //新增友情链接
    @Insert("insert into url(url) value (url)")
    Integer insertURL(URL url);
    //修改友情链接
    @Update("update url set url = #{url} where url_id = #{urlId}")
    Integer updateURL(URL url);
    //删除友情链接
    @Delete("delete from url where url_id = #{urlId}")
    Integer deleteURL(Long urlId);
}
