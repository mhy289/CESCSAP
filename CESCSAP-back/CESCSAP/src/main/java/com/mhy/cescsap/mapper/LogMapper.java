package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LogMapper {
    //查询一条日志
    @Select("select * from log where log_id = #{logId}")
    Log getLogById(Integer logId);

    //查询所有日志
    @Select("select * from log")
    List<Log> getAllLogs();

    //增加一个日志
    @Insert("insert into log(log_id,event_type,event_content,event_time,event_user,event_source,event_remark,event_status) values(#{logId}, #{eventType}, #{eventContent}, #{eventTime},#{eventUser}, #{eventSource}, #{eventRemark}, #{eventStatus})")
    Integer addLog(Log log);

    // 删除一个日志
    @Select("delete from log where log_id = #{logId}")
    Integer deleteLog(Integer logId);

    // 更新一个日志
    @Update("update log set event_type = #{eventType}, event_content = #{eventContent}, event_time = #{eventTime}, event_user = #{eventUser}, event_source = #{eventSource}, event_remark = #{eventRemark}, event_status = #{eventStatus} where log_id = #{logId}")
    Integer updateLog(Log log);

    // 查询某一事件的日志
}
