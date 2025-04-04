package com.mhy.cescsap.service;

import com.mhy.cescsap.pojo.QuestionAnswer;

import java.util.List;

public interface QuestionAnswerService {
    //查看一条问答
    QuestionAnswer viewQuestionAnswer(Long qaId);

    //查看所有问答
    List<QuestionAnswer> getQuestionAnswers();

    //添加一条问答
    Integer addQuestionAnswer(QuestionAnswer questionAnswer);

    //修改一条问答
    Integer updateQuestionAnswer(QuestionAnswer questionAnswer);

    //删除一条问答
    Integer deleteQuestionAnswer(Long qaId);
}
