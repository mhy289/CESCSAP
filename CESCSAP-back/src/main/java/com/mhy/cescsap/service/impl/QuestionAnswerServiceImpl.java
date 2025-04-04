package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.QuestionAnswerMapper;
import com.mhy.cescsap.pojo.QuestionAnswer;
import com.mhy.cescsap.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    QuestionAnswerMapper questionAnswerMapper;

    @Override
    public QuestionAnswer viewQuestionAnswer(Long qaId) {
        return questionAnswerMapper.selectAnswerById(qaId);
    }

    @Override
    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswerMapper.selectAllAnswers();
    }

    @Override
    public Integer addQuestionAnswer(QuestionAnswer questionAnswer) {
        return questionAnswerMapper.insertQuestionAnswer(questionAnswer);
    }

    @Override
    public Integer updateQuestionAnswer(QuestionAnswer questionAnswer) {
        return questionAnswerMapper.updateQuestionAnswer(questionAnswer);
    }

    @Override
    public Integer deleteQuestionAnswer(Long qaId) {
        return questionAnswerMapper.deleteQuestionAnswer(qaId);
    }
}
