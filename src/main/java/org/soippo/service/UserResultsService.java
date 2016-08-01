package org.soippo.service;

import org.soippo.entity.Group;
import org.soippo.entity.User;
import org.soippo.entity.UserResults;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserResultsService {
    @Resource
    private UserResultsRepository userResultsRepository;

    public List<UserResults> findAll() {
        return userResultsRepository.findAll();
    }

    public void saveAll(List<UserResults> userResults) {
        userResults.forEach(item -> userResultsRepository.save(item));
    }

    public Map<Long, Map<Long, List<UserResults>>> collectResults() {
        return userResultsRepository.findAll().stream()
                .collect(Collectors.groupingBy(result -> result.getUser().getGroup().getId(),
                        Collectors.groupingBy(UserResults::getUserId)));
    }
}
