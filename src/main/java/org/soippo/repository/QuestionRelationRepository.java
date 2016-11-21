package org.soippo.repository;

import org.soippo.entity.QuestionRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRelationRepository extends JpaRepository<QuestionRelation, Long> {
}
