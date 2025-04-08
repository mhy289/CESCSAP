package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.PartitionMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Partition;
import com.mhy.cescsap.service.PartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartitionServiceImpl implements PartitionService {

    @Autowired
    PartitionMapper partitionMapper;


    @Override
    public List<Partition> getAllPartitions() {
        List<Partition> partitions = partitionMapper.getPartitions();
        if(partitions.isEmpty()){
            throw new BusinessException(ExceptionType.NO_DATA_FOUND,"没有分区数据");
        }
        return partitions;
    }

    @Override
    public Integer addPartition(Partition partition) {
        return partitionMapper.addPartition(partition);
    }

    @Override
    public Integer deletePartition(Long id) {
        return partitionMapper.deletePartition(id);
    }

    @Override
    public Integer updatePartition(Partition partition) {
        return partitionMapper.updatePartition(partition);
    }
}
