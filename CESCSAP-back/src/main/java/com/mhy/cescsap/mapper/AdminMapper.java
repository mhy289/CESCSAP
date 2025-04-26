package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where name = #{name}")
    Admin selectAdminByName(String name);

    // 创建管理员
    @Insert("insert into admin(name, password, contact, login_status, role) values(#{name}, #{password}, #{contact}, #{loginStatus}, #{role})")
    Integer createAdmin(Admin admin);

    // 根据账号查询管理员
    @Select("select * from admin where account = #{account}")
    Admin selectAdminByAccount(Long account);
}
