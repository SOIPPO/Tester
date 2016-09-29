package org.soippo.service;

import org.soippo.entity.*;
import org.soippo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<Module> findAll() {
        return interviewRepository.findAll();
    }

    public Module findOne(Long id) {
        Module module = interviewRepository.findOne(id);
        List<Long> questionsIds = module.getQuestions().stream().map(Question::getId).collect(Collectors.toList());
        Map<Long, String> questionTexts = module.getQuestions().stream().collect(Collectors.toMap(Question::getId, Question::getText));
        Map<Long, List<RelationAnswer>> answers = relationAnswerRepository
                .findByQuestionIdIn(questionsIds)
                .stream()
                .collect(Collectors.groupingBy(RelationAnswer::getQuestionId));


        List<QuestionRelation> questionList = answers.entrySet().stream().map(item ->
                new QuestionRelation()
                        .setQuestionId(item.getKey())
                        .setText(questionTexts.get(item.getKey()))
                        .setQuestions(answers.get(item.getKey()).stream().sorted((o1, o2) -> (Math.random() > 0.3) ? -1 : 1).collect(Collectors.toMap(RelationAnswer::getId, RelationAnswer::getText)))
                        .setAnswers(answers.get(item.getKey()).stream().map(RelationAnswer::getAnswer).sorted((o1, o2) -> (Math.random() > 0.3) ? -1 : 1).collect(Collectors.toList())))
                .collect(Collectors.toList());

        module.setQuestionRelations(questionList);
        return module;
    }

    public Module save(Module module) {
        List<Long> oldQuestions = new ArrayList<>();
        if (module.getId() != null) {
            oldQuestions = interviewRepository.findOne(module.getId())
                    .getQuestions()
                    .stream()
                    .filter(item -> item != null)
                    .map(Question::getId)
                    .collect(Collectors.toList());
        }
        Module saved = interviewRepository.save(module);

        List<Long> newQuestions = Optional.ofNullable(saved.getQuestions()).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .map(Question::getId)
                .collect(Collectors.toList());

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
