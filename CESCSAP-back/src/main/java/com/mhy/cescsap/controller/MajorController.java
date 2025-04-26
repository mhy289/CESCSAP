package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Major;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
public class MajorController {

    @Autowired
    MajorService majorService;

    @GetMapping("/majors")
    public Result getMajors(){
        return new Result(majorService.getMajors());
    }

    @PostMapping("/major")
    public Result addMajor(@RequestBody Major major){
        return new Result(majorService.addMajor(major));
    }

    @PutMapping("/major")
    public Result updateMajor(@RequestBody Major major){
        return new Result(majorService.updateMajor(major));
    }

    @DeleteMapping("/major/{id}")
    public Result deleteMajor(@PathVariable("id") Long id){
        return new Result(majorService.deleteMajor(id));
    }
}
