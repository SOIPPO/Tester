package org.soippo.service;

import org.soippo.entity.GroupModules;
import org.soippo.entity.Module;
import org.soippo.entity.Question;
import org.soippo.entity.QuestionRelation;
import org.soippo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleService {
    @Resource
    private InterviewRepository interviewRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupModuleRepository groupModuleRepository;
    @Resource
    private UserResultsRepository userResultsRepository;
    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private AnswerRepository answerRepository;
    @Resource
    private QuestionService questionService;
    @Resource
    private RelationAnswerRepository relationAnswerRepository;
    @Resource
    private QuestionRelationRepository questionRelationRepository;

    public List<Module> findAll() {
        return interviewRepository.findAll();
    }

    public Module findOne(Long id) {
        return interviewRepository.findOne(id);
    }

    public Module save(Module module) {
        List<Long> oldQuestions = new ArrayList<>();
        List<Long> oldRelations = new ArrayList<>();

        if (module.getId() != null) {
            Module savedModule = findOne(module.getId());
            oldQuestions = savedModule
                    .getQuestions()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Question::getId)
                    .collect(Collectors.toList());
            oldRelations = Optional.ofNullable(savedModule.getQuestionRelations()).orElse(new ArrayList<>())
                    .stream()
                    .map(QuestionRelation::getQuestionId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            module.getQuestionRelations().stream().filter(Objects::nonNull).forEach(item -> item.setModuleId(module.getId()));
            module.setQuestionRelations(module.getQuestionRelations().stream().filter(Objects::nonNull).map(
                    item -> item.setRelationAnswers(item.getRelationAnswers().stream().filter(Objects::nonNull).collect(Collectors.toList()))
            ).collect(Collectors.toList()));
        }
        Module saved = interviewRepository.save(module);

        List<Long> newQuestions = Optional.ofNullable(saved.getQuestions()).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .map(Question::getId)
                .collect(Collectors.toList());


        List<Long> newRelationQuestions = Optional.ofNullable(saved.getQuestionRelations()).orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .map(QuestionRelation::getQuestionId)
                .collect(Collectors.toList());

        oldRelations.removeAll(newRelationQuestions);
        oldRelations.forEach(item -> {
            questionRelationRepository.delete(item);
            relationAnswerRepository.deleteByQuestionId(item);
        });


        oldQuestions.removeAll(newQuestions);
        oldQuestions.forEach(item -> {
            userResultsRepository.deleteByQuestionId(item);
            answerRepository.deleteByQuestionId(item);
            questionRepository.delete(item);

        });
        return saved;
    }

    public void delete(Long moduleId) {
        questionService.deleteByModuleId(moduleId);
        groupModuleRepository.deleteByModuleId(moduleId);
        groupModuleRepository.flush();
        interviewRepository.delete(moduleId);
    }

    public List<Module> availableModulesForUser(Long userId) {
        Predicate<LocalDate> isToday = date -> date.getYear() == LocalDate.now().getYear() && date.getDayOfYear() == LocalDate.now().getDayOfYear();
        return groupModuleRepository.findByGroupId(userRepository.findOne(userId).getGroupId())
                .stream()
                .filter(item -> isToday.test(item.getFinalDate()) || isToday.test(item.getIncomingDate()))
                .map(GroupModules::getModule)
                .collect(Collectors.toList());
    }
}
