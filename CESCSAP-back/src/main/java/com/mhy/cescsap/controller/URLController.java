package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.pojo.URL;
import com.mhy.cescsap.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class URLController {

    @Autowired
    URLService urlService;

    //查看所有友情链接
    @GetMapping("/urls")
    public Result getAllURLs() {
        return new Result(urlService.getURLs());
    }

    // 新增友情链接
    @PostMapping("/url")
    public Result addURL(String url) {
        Integer i = urlService.addURL(new URL(url));
        if(i == null) {
            throw new BusinessException(ExceptionType.INTERNAL_ERROR,"新增失败");
        }else{
            return new Result("新增成功");
        }
    }

    // 修改友情链接
    @PutMapping("/url")
    public Result updateURL(URL u) {
        Integer i = urlService.updateURL(u);
        if(i == null) {
            throw new BusinessException(ExceptionType.INTERNAL_ERROR,"修改失败");
        }else{
            return new Result("修改成功");
        }
    }

    // 删除友情链接
    @DeleteMapping("/url/{id}")
    public Result deleteURL(@PathVariable Long id) {
        Integer i = urlService.deleteURL(id);
        if(i == null) {
            throw new BusinessException(ExceptionType.INTERNAL_ERROR,"删除失败");
        }else{
            return new Result("删除成功");
        }
    }
}
