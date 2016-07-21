package org.soippo.service;

import org.soippo.entity.UserResults;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserResultsService {
    @Resource
    private UserResultsRepository userResultsRepository;

    public List<UserResults> findAll() {
        return userResultsRepository.findAll();
    }

    public void saveAll(List<UserResults> userResults) {
        userResults.stream().forEach(item -> userResultsRepository.save(item));
    }
}
