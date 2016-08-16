package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.exceptions.NotUniqueEmailException;
import org.soippo.exceptions.NotUniqueUserException;
import org.soippo.exceptions.UserValidationException;
import org.soippo.repository.UserModuleRepository;
import org.soippo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Resource
    private UserRepository userRepository;


    @Resource
    private UserModuleRepository userModuleRepository;

    public User saveUser(User user) throws UserValidationException {
        if (user.getId() == null) {
            if (!checkUniqueEmail(user.getEmail())) {
                throw new NotUniqueEmailException();
            }
            if (!checkUniqueUser(user)) {
                throw new NotUniqueUserException();
            }
        }
        if(user.getId() != null) {
            userModuleRepository.deleteByUserId(user.getId());
        }
        User saved = userRepository.save(user);
        return userRepository.findOne(saved.getId());
    }

    public void deleteUser(Long userId) {
        userRepository.delete(userId);
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

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}
