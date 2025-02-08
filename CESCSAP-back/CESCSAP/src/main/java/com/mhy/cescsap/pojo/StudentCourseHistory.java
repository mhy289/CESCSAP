package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseHistory {
    private String courseName;
    private Double score;
    private String teacherName;
    private Double gpa;
    private Date examDate;
    private Double credit;

    public void setGpa() {
        if(this.score<60){
            this.gpa = 0.0;
        } else{
            this.gpa = (this.score - 50) / 10;
        }
    }
}
