package com.quizora.question_service.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quizora.question_service.enums.Category;
import com.quizora.question_service.models.Question;
import com.quizora.question_service.models.Response;
import com.quizora.question_service.models.QuestionWrapper;
import com.quizora.question_service.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping (path="question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    
    @GetMapping(path="getQuestions")
    public ResponseEntity<List<Question>> question(){
        return questionService.getAllQuestions();
    }

    @GetMapping(path="category/{category}")
    public ResponseEntity<List<Question>> category(@PathVariable("category") Category category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping(path="add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.saveQuestion(question);
    }

    @DeleteMapping(path="del/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") int id){
        return questionService.deleteQuestionById(id);
    }

    @DeleteMapping(path="del")
    public ResponseEntity<String> deleteQuestionByQuestionTitle(@RequestParam("questionTitle") String questionTitle){
        return questionService.deleteQuestionByQuestionTitle(questionTitle);
    }

    @GetMapping(path = "generate")
    public ResponseEntity<List<Integer>> generateQuestion(@RequestParam Category category, @RequestParam Integer numQ){
        return questionService.generateQuestionsByCategory(category, numQ);
    }

    @PostMapping(path="getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds){
        return questionService.getQuestions(questionIds);
    }

    @PostMapping(path="calculateScore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses){
        return questionService.calculateScoreByResponces(responses);
    }
}
