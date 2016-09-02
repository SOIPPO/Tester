package org.soippo.service;

import org.soippo.entity.Module;
import org.soippo.entity.User;
import org.soippo.entity.UserResults;
import org.soippo.entity.results.GroupModuleResults;
import org.soippo.entity.results.ModuleResults;
import org.soippo.entity.results.UserModuleResults;
import org.soippo.entity.results.department.DepartmentModuleResult;
import org.soippo.entity.results.department.DepartmentResults;
import org.soippo.repository.InterviewRepository;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class UserResultsService {
    @Resource
    private UserResultsRepository userResultsRepository;
    @Resource
    private InterviewRepository interviewRepository;


    public void saveAll(List<UserResults> userResults) {
        userResultsRepository.save(userResults);
    }

    public List<GroupModuleResults> collectResultsByUser(Long userId) {
        return collectResults(userResultsRepository.findAllByUserId(userId));
    }

    public List<GroupModuleResults> collectResults() {
        return collectResults(userResultsRepository.findAll());
    }

    private List<GroupModuleResults> collectResults(List<UserResults> results) {
        LongStream groups = results
                .stream()
                .mapToLong(item -> item.getUser().getGroupId())
                .distinct();

        Map<Long, Module> modules = interviewRepository.findAll().stream().collect(Collectors.toMap(Module::getId, item -> item));

        Map<User, Map<Date, Map<Long, List<UserResults>>>> userResultsByDateAndModuleId = results.stream()
                .collect(Collectors.groupingBy(UserResults::getUser,
                        Collectors.groupingBy(UserResults::getDate,
                                Collectors.groupingBy(item -> item.getQuestion().getModuleId()))));

        List<UserModuleResults> modulesResults = userResultsByDateAndModuleId.entrySet().stream().map(
                userResult -> new UserModuleResults()
                        .setUser(userResult.getKey())
                        .setModuleResultsList(
                                userResult.getValue().entrySet().stream().flatMap(
                                        dayResult -> dayResult.getValue().entrySet().stream().map(
                                                moduleResult -> new ModuleResults()
                                                        .setDate(dayResult.getKey())
                                                        .setModuleId(moduleResult.getKey())
                                                        .setModuleTitle(modules.get(moduleResult.getKey()).getTitle())
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

        return groups.mapToObj(item -> new GroupModuleResults()
                .setGroupId(item)
                .setUserModuleResults(modulesResults
                        .stream()
                        .filter(res -> res.getUser().getGroupId().equals(item))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<DepartmentModuleResult> collectResultsForDepartment() {
        List<UserResults> userResultses = userResultsRepository.findAll();
        Map<Long, String> moduleTitles = interviewRepository.findAll().stream().collect(Collectors.toMap(Module::getId, Module::getTitle));

        DepartmentResults departmentResults = new DepartmentResults();
        userResultses.forEach(departmentResults::addUserResult);

        return departmentResults
                .getModules()
                .entrySet()
                .stream()
                .map(item -> (DepartmentModuleResult)item.getValue().setModuleTitle(moduleTitles.get(item.getKey())))
                .collect(Collectors.toList());
    }
}

