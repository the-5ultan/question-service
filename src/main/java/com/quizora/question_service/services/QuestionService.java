package com.quizora.question_service.services;


import com.quizora.question_service.enums.Category;
import com.quizora.question_service.models.Question;
import com.quizora.question_service.models.QuestionWrapper;
import com.quizora.question_service.models.Response;
import com.quizora.question_service.repositories.QuestionRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() throws NullPointerException{
        try {
            return new ResponseEntity<>(questionRepository.findAll(),HttpStatus.OK );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(Category category) throws NullPointerException{
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> saveQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Successfully Created",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> deleteQuestionById(int id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("successfully deleted"+id,HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to Delete",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> deleteQuestionByQuestionTitle(String questionTitle) {
        try {
            questionRepository.deleteQuestionByQuestionTitle(questionTitle);
            return  new ResponseEntity<>("successfully deleted "+questionTitle,HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to Delete",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<List<Integer>> generateQuestionsByCategory(Category category, Integer numQ) {
        try {
            return new ResponseEntity<>(questionRepository.generateQuestionsByCategory(category.name(), numQ), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds) {
        try{
            List<QuestionWrapper> list = new ArrayList<>();
            for(Integer questionId : questionIds){
                Question question = questionRepository.findById(questionId).get();
                QuestionWrapper questionWrapper = new QuestionWrapper();
                questionWrapper.setId(questionId);
                questionWrapper.setOption1(question.getOption1());
                questionWrapper.setOption2(question.getOption2());
                questionWrapper.setOption3(question.getOption3());
                questionWrapper.setOption4(question.getOption4());
                questionWrapper.setQuestionTitle(question.getQuestionTitle());
                list.add(questionWrapper);
            }
            return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> calculateScoreByResponces(List<Response> responses) {
        try {
            Integer score = 0;
            for(Response response : responses){
                Question question = questionRepository.findById(response.getId()).get();
                String answer = question.getRightOption();
                if(response.equals(question.getRightOption())){
                    score++;
                }
            }
            return new ResponseEntity<>(score,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
