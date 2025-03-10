package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    private Long pageNum;
    private Long pageSize;
    private String search1;
    private String search2;
    private String courseName;
    private String teacherName;
    private Date startDate;
    private Date endDate;

}
