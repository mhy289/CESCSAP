package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MajorMapper {

    @Select("select * from major")
    List<Major> getAllMajor();

    @Select("select * from major where major_id = #{majorId}")
    Major getMajorById(Long majorId);

    @Select("select * from major where major_name = #{majorName}")
    Major getMajor(String majorName);

    @Insert("insert into major(major_name,department_id) values(#{majorName},#{departmentId})")
    Integer addMajor(Major major);

    @Update("update major set major_name = #{majorName},department_id = #{departmentId} where major_id = #{majorId}")
    Integer updateMajor(Major major);

    @Delete("delete from major where major_id = #{majorId}")
    Integer deleteMajor(Long majorId);
}
