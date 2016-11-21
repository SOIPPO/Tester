package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.GroupModules;
import org.soippo.entity.User;
import org.soippo.exceptions.NotUniqueUserException;
import org.soippo.exceptions.UserValidationException;
import org.soippo.repository.GroupModuleRepository;
import org.soippo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupModuleRepository groupModuleRepository;

    public User saveUser(User user) throws UserValidationException {
        if (user.getId() == null) {
            if (!checkUniqueUser(user)) {
                throw new NotUniqueUserException();
            }
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

    public boolean isModuleAvailableForUser(Long userId, Long moduleId) {
        List<GroupModules> groupModules = groupModuleRepository
                .findByGroupId(userRepository.findOne(userId).getGroupId())
                .stream()
                .filter(item -> item.getFinalDate().equals(LocalDate.now()) || item.getIncomingDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        return groupModules.stream().anyMatch((item) -> item.getModule().getId().equals(moduleId));
    }
}
