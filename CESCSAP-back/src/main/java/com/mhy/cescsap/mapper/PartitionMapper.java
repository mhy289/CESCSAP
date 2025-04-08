package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.Partition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PartitionMapper {

    @Select("select * from partitions")
    List<Partition> getPartitions();

    @Insert("insert into partitions(partition_title, partition_content, partition_time) VALUES (#{partitionTitle},#{partitionContent},#{partitionTime})")
    Integer addPartition(Partition partition);

    @Update("update partitions set partition_title = #{partitionTitle},partition_content = #{partitionContent}, partition_time = #{partitionTime} where partition_id = #{partitionId}")
    Integer updatePartition(Partition partition);

    @Delete("delete from partitions where partition_id = #{partitionId}")
    Integer deletePartition(Long partitionId);
}
