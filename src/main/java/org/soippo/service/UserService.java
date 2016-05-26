package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.exceptions.NotUniqueEmailException;
import org.soippo.exceptions.NotUniqueUserException;
import org.soippo.exceptions.UserValidationException;
import org.soippo.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public User saveUser(User user) throws UserValidationException {
        if(user.getId() != null && userRepository.findOne(user.getId()) == null) {
            if (!checkUniqueEmail(user.getEmail())) {
                throw new NotUniqueEmailException("Email must be unique!");
            }

            if (!checkUniqueUser(user)) {
                throw new NotUniqueUserException("User already exists!");
            }
        }

        return userRepository.save(user);
    }

    public List<User> findUsersInGroup(Group group) {
        return userRepository.findByGroup_Id(group.getId());
    }

    private boolean checkUniqueEmail(String email) {
        return userRepository.findByEmail(email) == null;
    }

    private boolean checkUniqueUser(User user) {
        List<User> users = userRepository.findAllByFirstNameAndLastNameAndMiddleNameAndGroup(user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getGroup());

        return (users == null || users.isEmpty());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
