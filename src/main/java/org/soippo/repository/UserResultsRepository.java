package org.soippo.repository;

import org.soippo.entity.UserResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResultsRepository extends JpaRepository<UserResults, Long>{
}
