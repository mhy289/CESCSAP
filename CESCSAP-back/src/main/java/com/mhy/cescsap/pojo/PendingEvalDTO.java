package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingEvalDTO {
    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
}
