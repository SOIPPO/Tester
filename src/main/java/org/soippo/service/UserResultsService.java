package org.soippo.service;

import org.soippo.entity.User;
import org.soippo.entity.UserResults;
import org.soippo.entity.results.GroupModuleResults;
import org.soippo.entity.results.ModuleResults;
import org.soippo.entity.results.UserModuleResults;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
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

    public List<GroupModuleResults> collectResults() {
        List<UserResults> results = userResultsRepository.findAll();

        List<Long> groups = results
                .stream()
                .map(item -> item.getUser().getGroupId())
                .distinct()
                .collect(Collectors.toList());

        Map<User, Map<Date, Map<Long, List<UserResults>>>> userResultsByDateAndModuleId = results.stream()
                .collect(Collectors.groupingBy(UserResults::getUser,
                        Collectors.groupingBy(UserResults::getDate,
                                Collectors.groupingBy(item -> item.getQuestion().getInterviewId()))));

        List<UserModuleResults> modulesResults = userResultsByDateAndModuleId.entrySet().stream().map(
                userResult -> new UserModuleResults()
                        .setUser(userResult.getKey())
                        .setModuleResultsList(
                                userResult.getValue().entrySet().stream().flatMap(
                                        dayResult -> dayResult.getValue().entrySet().stream().map(
                                                moduleResult -> new ModuleResults()
                                                        .setDate(dayResult.getKey())
                                                        .setModuleId(moduleResult.getKey())
                                                        .setTotalQuestions((long) moduleResult.getValue().size())
                                                        .setCorrectAnswersCount(moduleResult
                                                                .getValue()
                                                                .stream()
                                                                .filter(UserResults::getIsCorrect)
                                                                .count())
                                        )
                                ).collect(Collectors.toList())
                        )
        ).collect(Collectors.toList());

        return groups.stream().map(item -> new GroupModuleResults()
                .setGroupId(item)
                .setUserModuleResults(modulesResults
                        .stream()
                        .filter(res -> res.getUser().getGroupId().equals(item))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}

