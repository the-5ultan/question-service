package com.quizora.question_service.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.quizora.question_service.enums.Category;
import com.quizora.question_service.models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    //We can convert the Category to String but this can
    List<Question> findByCategory(Category category);
    void deleteQuestionByQuestionTitle(String questionTitle);
    @Query(//For the PostgreSQL the RAND() should be changed to RANDOM()
            value = "SELECT * FROM question " +
                    "WHERE category = :category " +
                    "ORDER BY RAND() " +
                    "LIMIT :numQ",
            nativeQuery = true
    )
    List<Question> createQuizRandomly(
            @Param("category") String category,
            @Param("numQ") int numQ
    );
    @Query(//For the PostgreSQL the RAND() should be changed to RANDOM()
            value = "SELECT q.id FROM question q " +
                    "WHERE category = :category " +
                    "ORDER BY RAND() " +
                    "LIMIT :numQ",
            nativeQuery = true
    )
    List<Integer> generateQuestionsByCategory(String category,Integer numQ);
}