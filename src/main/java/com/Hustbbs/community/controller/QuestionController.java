package com.Hustbbs.community.controller;

import com.Hustbbs.community.dto.CommentDTO;
import com.Hustbbs.community.dto.QuestionDTO;
import com.Hustbbs.community.enums.CommentTypeEnum;
import com.Hustbbs.community.exception.CustomizeErrorCode;
import com.Hustbbs.community.exception.CustomizeException;
import com.Hustbbs.community.model.Question;
import com.Hustbbs.community.service.CommentService;
import com.Hustbbs.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDTO questionDTO = questionService.getById(questionId);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        List<Question> recommands = questionService.getRecommand();
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("recommands",recommands);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
