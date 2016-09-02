package org.soippo.service;

import org.soippo.entity.Answer;
import org.soippo.entity.Question;
import org.soippo.repository.AnswerRepository;
import org.soippo.repository.QuestionRepository;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {
    @Resource
    private AnswerRepository answerRepository;
    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private UserResultsRepository userResultsRepository;

    public void deleteByModuleId(Long moduleId) {
        List<Question> questions = questionRepository.findByModuleId(moduleId);
        questions.stream().map(Question::getId).forEach(item -> {
            userResultsRepository.deleteByQuestionId(item);
            answerRepository.deleteByQuestionId(item);
        });
        questionRepository.deleteByModuleId(moduleId);
    }

    private Map<Long, List<Answer>> getCorrectAnswers(List<Long> questionIds) {
        return answerRepository
                .findByQuestionIdInAndIsCorrect(questionIds, Boolean.TRUE)
                .stream()
                .collect(Collectors.groupingBy(Answer::getQuestionId));
    }

    public Map<Long, Boolean> checkAnswers(Map<Long, List<Long>> userResult) {
        Map<Long, List<Long>> correctAnswers = getCorrectAnswers(userResult
                .keySet()
                .stream()
                .collect(Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry
                        .getValue()
                        .stream()
                        .map(Answer::getId)
                        .sorted()
                        .collect(Collectors.toList())));

        return userResult
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                item -> userResult.get(item.getKey())
                                        .stream()
                                        .sorted()
                                        .collect(Collectors.toList())
                                        .equals(correctAnswers.get(item.getKey()))));
    }


}
