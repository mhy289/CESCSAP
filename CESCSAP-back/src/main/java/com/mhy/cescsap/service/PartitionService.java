package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.Partition;

import java.util.List;

public interface PartitionService {
    //查看全部分区
    List<Partition> getAllPartitions();

    //新增分区
    Integer addPartition(Partition partition);

    //删除分区
    Integer deletePartition(Long id);

    //修改分区
    Integer updatePartition(Partition partition);
}
