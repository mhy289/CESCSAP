package com.mhy.cescsap.controller;

import com.mhy.cescsap.pojo.Link;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LinkController {

    @Autowired
    LinkService linkService;

    // 获取所有超链
    @GetMapping("/links")
    public Result getAllLinks() {
        return new Result(linkService.getLinks());
    }

    // 按id获取超链
    @GetMapping("/links/{id}")
    public Result getLinkById(@PathVariable Long id) {
        return new Result(linkService.getLinkById(id));
    }

    // 新增超链
    @PostMapping("/link")
    public Result addLink(@RequestBody Link link) {
        Integer id = linkService.addLink(link);
        if(id==0){
            return new Result("非法链接","非法链接",201);
        }
        return new Result(id);
    }

    // 更新超链
    @PutMapping("/link")
    public Result updateLink(@RequestBody Link link) {
        //判断link是否传入
        if (link == null || link.getLinkId() == null) {
            return new Result("link is null");
        }
        Integer i = linkService.updateLink(link);
        if (i == 0) {
            return new Result("非法链接","非法链接",201);
        }
        return new Result(i);
    }

    // 删除超链
    @DeleteMapping("/link/{id}")
    public Result deleteLink(@PathVariable Long id) {
        return new Result(linkService.deleteLink(id));
    }

    //分页查询超链
    @GetMapping("/links/page/{page}/size/{size}")
    public Result getLinksByPage(@PathVariable Integer page, @PathVariable Integer size) {
        //暂停10s
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result(linkService.getLinksByPage(page, size));
    }
}
