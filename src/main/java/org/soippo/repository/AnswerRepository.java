package org.soippo.repository;

import org.soippo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionIdAndIsCorrect(Long questionId, Boolean isCorrect);

    List<Answer> findByQuestionIdInAndIsCorrect(Collection<Long> questionId, Boolean isCorrect);

    void deleteByQuestionId(Long item);
}
