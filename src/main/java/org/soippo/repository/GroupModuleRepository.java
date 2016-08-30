package org.soippo.repository;

import org.soippo.entity.GroupModules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupModuleRepository extends JpaRepository<GroupModules, Long>{
    List<GroupModules> findByModuleId(Long moduleId);
    void deleteByModuleId(Long interviewId);
    void deleteByGroupId(Long groupId);
    List<GroupModules> findByGroupId(Long groupId);
}
