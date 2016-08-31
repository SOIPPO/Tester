package org.soippo.repository;

import org.soippo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    void deleteByModuleId(Long moduleId);

    List<Question> findByModuleId(Long moduleId);
}
