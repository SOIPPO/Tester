package org.soippo.service;

import org.soippo.entity.Answer;
import org.soippo.entity.Question;
import org.soippo.entity.RelationAnswer;
import org.soippo.repository.AnswerRepository;
import org.soippo.repository.QuestionRepository;
import org.soippo.repository.RelationAnswerRepository;
import org.soippo.repository.UserResultsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class QuestionService {
    @Resource
    private AnswerRepository answerRepository;
    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private UserResultsRepository userResultsRepository;
    @Resource
    private RelationAnswerRepository relationAnswerRepository;

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

    private Map<Long, List<RelationAnswer>> getCorrectAnswersForRelationQuestions(List<Long> ids) {
        return relationAnswerRepository.findByQuestionIdIn(ids).stream().collect(Collectors.groupingBy(item -> item.getQuestionId()));
    }

    public Map<Long, Boolean> checkAnswers(Map<Long, String> userResult) {
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

        Map<Long, List<RelationAnswer>> correctAnswersForRelationQuestions = getCorrectAnswersForRelationQuestions(userResult
                .keySet()
                .stream()
                .collect(Collectors.toList()));

        Function<Long, Boolean> checkIsAnswerCorrect = (questionId) ->
                Arrays.stream(userResult.get(questionId).split(","))
                        .map(item -> Long.parseLong(item.trim()))
                        .sorted()
                        .collect(Collectors.toList())
                        .equals(correctAnswers.get(questionId));

        Function<Long, String> getCorrectAnswerForRelationQuestion = (questionId) -> correctAnswersForRelationQuestions
                .get(questionId)
                .stream()
                .map(item -> String.format("%s->%s", item.getText(), item.getAnswer()))
                .sorted()
                .collect(Collectors.joining(";"));

        Function<Long, Boolean> checkIsAnswerRelationCorrect = (questionId) -> Stream
                .of(userResult.get(questionId).split(";"))
                .sorted()
                .collect(Collectors.joining(";"))
                .equals(getCorrectAnswerForRelationQuestion.apply(questionId));


        Map<Long, Boolean> userResultsForSimpleQuestions = userResult.entrySet()
                .stream()
                .filter(item -> correctAnswers.containsKey(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, item -> checkIsAnswerCorrect.apply(item.getKey())));

        Map<Long, Boolean> userResultsForRelationQuestions = userResult.entrySet()
                .stream()
                .filter(item -> correctAnswersForRelationQuestions.containsKey(item.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, item -> checkIsAnswerRelationCorrect.apply(item.getKey())));


        Map<Long, Boolean> result = new HashMap<>();

        result.putAll(userResultsForSimpleQuestions);
        result.putAll(userResultsForRelationQuestions);
        return result;
    }


}
