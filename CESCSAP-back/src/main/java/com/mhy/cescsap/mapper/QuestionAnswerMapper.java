package com.mhy.cescsap.mapper;

import com.mhy.cescsap.pojo.QuestionAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionAnswerMapper {
    //查看一条问答
    @Select("select * from questionanswer where  qa_id = #{qaId}")
    QuestionAnswer selectAnswerById(Long id);

    //查看所有问答
    @Select("select * from questionanswer")
    List<QuestionAnswer> selectAllAnswers();

    //新增一条问答
    @Insert("insert into questionanswer(question,answer) values (#{question},#{answer})")
    Integer insertQuestionAnswer(QuestionAnswer questionAnswer);

    //修改一条问答
    @Update("update questionanswer set question = #{question}, answer = #{answer} where qa_id = #{qaId}")
    Integer updateQuestionAnswer(QuestionAnswer questionAnswer);

    //删除一条问答
    @Delete("delete from questionanswer where qa_id = #{qaId}")
    Integer deleteQuestionAnswer(Long id);
}
