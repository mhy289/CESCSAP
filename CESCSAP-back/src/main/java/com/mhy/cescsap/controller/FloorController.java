package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Floor;
import com.mhy.cescsap.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class FloorController {

    //查看该分区该帖子所有楼层
    @PostMapping("/floor")
    public Result getFloor(Floor floor){

        return new Result();
    }

}
