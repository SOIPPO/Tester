package org.soippo.repository;

import org.soippo.entity.UserResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserResultsRepository extends JpaRepository<UserResults, Long>{
    List<UserResults> findAllByUserId(Long userId);

    void deleteByQuestionId(Long ids);
}
