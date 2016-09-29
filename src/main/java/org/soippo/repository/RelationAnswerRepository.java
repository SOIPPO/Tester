package org.soippo.repository;

import org.soippo.entity.RelationAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationAnswerRepository extends JpaRepository<RelationAnswer, Long> {
    List<RelationAnswer> findByQuestionIdIn(Iterable<Long> questionId);
}
