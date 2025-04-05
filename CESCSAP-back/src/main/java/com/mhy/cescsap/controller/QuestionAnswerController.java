package com.mhy.cescsap.controller;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.QuestionAnswer;
import com.mhy.cescsap.pojo.Result;
import com.mhy.cescsap.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionAnswerController {

    @Autowired
    QuestionAnswerService questionAnswerService;

    //获取一条问答
    @GetMapping("/qa/{qaId}")
    public Result getQuestionAnswer(@PathVariable Long qaId) {
        return new Result(questionAnswerService.viewQuestionAnswer(qaId));
    }

    // 获取所有问答
    @GetMapping("/qas")
    public Result getAllQuestionAnswers() {
        return new Result(questionAnswerService.getQuestionAnswers());
    }

    // 新增问答
    @PostMapping("/qa")
    public Result addQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        Integer i = questionAnswerService.addQuestionAnswer(questionAnswer);
        if(i>0){
            return new Result("添加成功");
        }
        else{
            throw new BusinessException(ExceptionType.INTERNAL_SERVER_ERROR,"添加失败");
        }
    }

    // 修改问答
    @PutMapping("/qa")
    public Result updateQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        Integer i = questionAnswerService.updateQuestionAnswer(questionAnswer);
        if(i>0){
            return new Result("修改成功");
        }
        else{
            throw new BusinessException(ExceptionType.INTERNAL_SERVER_ERROR,"修改失败");
        }
    }

    // 删除问答
    @DeleteMapping("/qa/{qaId}")
    public Result deleteQuestionAnswer(@PathVariable Long qaId) {
        Integer i = questionAnswerService.deleteQuestionAnswer(qaId);
        if(i>0){
            return new Result("删除成功");
        }
        else{
            throw new BusinessException(ExceptionType.INTERNAL_SERVER_ERROR,"删除失败");
        }
    }

    // 分页获取问答
    @GetMapping("/qas/page/{page}/size/{size}")
    public Result getQasByPage(@PathVariable Integer page, @PathVariable Integer size){
        return new Result(questionAnswerService.getQasByPage(page, size));
    }
}
