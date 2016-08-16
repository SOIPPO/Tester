package org.soippo.repository;

import org.soippo.entity.UserModules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModuleRepository extends JpaRepository<UserModules, Long>{
    List<UserModules> findByModuleId(Long moduleId);
    void deleteByModuleId(Long interviewId);
    void deleteByUserId(Long userId);
}
