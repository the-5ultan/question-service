package com.quizora.question_service.models;

import lombok.Data;
import jakarta.persistence.*;
import com.quizora.question_service.enums.Category;
import com.quizora.question_service.enums.DifficultyLevel;

@Entity
@Data
@Table(name = "question")//Bug solved by changing it to the correct entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionTitle;
    private String rightOption;


}
