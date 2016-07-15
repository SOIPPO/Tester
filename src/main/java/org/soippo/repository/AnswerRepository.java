package org.soippo.repository;

import org.soippo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionIdAndIsCorrect(Long questionId, Boolean isCorrect);
    List<Answer> findByQuestionIdInAndIsCorrect(List<Long> questionId, Boolean isCorrect);
}
