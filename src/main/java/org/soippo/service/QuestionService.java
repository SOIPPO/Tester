package org.soippo.service;

import org.soippo.entity.Answer;
import org.soippo.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Resource
    private AnswerRepository answerRepository;

    public List<Answer> getCorrectAnswers(Long questionId) {
        return answerRepository.findByQuestionIdAndIsCorrect(questionId, Boolean.TRUE);
    }

    public Map<Long, List<Answer>> getCorrectAnswers(List<Long> questionIds) {
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
