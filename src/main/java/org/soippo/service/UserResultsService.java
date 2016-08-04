package org.soippo.service;

import org.soippo.entity.UserResults;
import org.soippo.entity.results.GroupModuleResults;
import org.soippo.entity.results.ModuleResults;
import org.soippo.entity.results.UserModuleResults;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    public List<GroupModuleResults> collectResults() {
        List<UserResults> results = userResultsRepository.findAll();

        List<Long> groups = results
                .stream()
                .map(item -> item.getUser().getGroupId())
                .distinct()
                .collect(Collectors.toList());

        List<UserModuleResults> modulesResults = results.stream().collect(Collectors.groupingBy(UserResults::getUser)).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        item -> item.getKey(),
                        item -> item.getValue().stream()
                                .collect(Collectors.groupingBy(result -> result.getQuestion().getInterviewId()))
                                .entrySet().stream()
                                .map(result -> new ModuleResults()
                                        .setTotalQuestions((long) result.getValue().size())
                                        .setModuleId(result.getKey())
                                        .setCorrectAnswersCount(result.getValue().stream().filter(UserResults::getIsCorrect).count()))
                                .collect(Collectors.toList())
                )).entrySet().stream()
                .map(item -> new UserModuleResults()
                        .setModuleResultsList(item.getValue())
                        .setUser(item.getKey()))
                .collect(Collectors.toList());

        return groups.stream().map(item -> new GroupModuleResults()
                .setGroupId(item)
                .setUserModuleResults(modulesResults
                        .stream()
                        .filter(res -> res.getUser().getGroupId().equals(item))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}

