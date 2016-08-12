package org.soippo.repository;

import org.soippo.entity.UserModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserModuleRepository extends JpaRepository<UserModules, Long>{
    List<UserModules> findAllByUserId(Long userId);
}
