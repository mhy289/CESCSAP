package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//专业
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    private Long majorId;
    private String majorName;
    private Long departmentId;
}
