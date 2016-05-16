package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.exceptions.NotUniqueEmailException;
import org.soippo.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public User saveUser(User user) throws NotUniqueEmailException{
        if(checkUniqueEmail(user.getEmail())) {
            throw new NotUniqueEmailException("Email must be unique!");
        }
        return userRepository.save(user);
    }

    public List<User> findUsersInGroup(Group group) {
        return userRepository.findByGroup_Id(group.getId());
    }

    private boolean checkUniqueEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
