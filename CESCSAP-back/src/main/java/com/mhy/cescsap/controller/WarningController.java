package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.Warning;
import com.mhy.cescsap.service.WarningService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class WarningController {

    @Autowired
    WarningService warningService;


    //教师发送预警（或更新预警内容）
    //接口用于当学生成绩不合格时，教师更新预警记录中的 warningContent 与 sendDate 等信息
    @PutMapping("/send")
    public Result sendWarning(@RequestBody Warning warning) {
        // 设置当前时间为发送日期
        warning.setSendDate(new Date());
        // 调用业务层更新预警记录（包括预警内容和教师ID等信息）
        Integer i = warningService.updateWarningByTeacher(warning);
        if (i != null && i>0) {
            return new Result(i, "预警发送成功", 200);
        } else {
            throw new BusinessException(ExceptionType.INTERNAL_SERVER_ERROR, "预警发送失败");
        }
    }


     //学生回应预警
     //接口用于学生提交对教师预警的响应，将 studentResponse 更新到预警记录中

    @PutMapping("/response")
    public Result respondWarning(@RequestBody Warning warning) {
        // 根据预警ID更新学生回应内容
        Integer i = warningService.updateStudentResponse(warning.getWarningId(), warning.getStudentResponse());
        if(i!=null && i>0){
            return new Result(i, "预警回应成功", 200);
        }else {
            throw new BusinessException(ExceptionType.INTERNAL_SERVER_ERROR, "预警回应失败");
        }
    }

    //教师查询可以发送的预警
    @GetMapping("/teacher/warnings/{teacherId}")
    public Result getWarningsForTeacher(@PathVariable Long teacherId) {
            List<Warning> warnings = warningService.getWarningsForTeacher(teacherId);
            return new Result( warnings, "查询成功",200);
    }
    //学生查询待处理的预警
    @GetMapping("/student/warnings/{studentId}")
    public Result getWarningsForStudent(@PathVariable Long studentId) {
        List<Warning> warnings = warningService.getWarningsForStudent(studentId);
        return new Result(warnings, "查询成功", 200);
    }

}
