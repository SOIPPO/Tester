package org.soippo.repository;

import org.soippo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByGroup_Id(Long groupId);
    User findByEmail(String email);
}
