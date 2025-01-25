package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where name = #{name}")
    Admin selectAdminByName(String name);

}
