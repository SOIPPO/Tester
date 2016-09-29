package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import java.util.List;
import java.util.Map;

public class QuestionRelation {
    @JsonView(View.Normal.class)
    private Long questionId;
    @JsonView(View.Normal.class)
    private String text;
    @JsonView(View.Normal.class)
    private Map<Long, String> questions;
    @JsonView(View.Normal.class)
    private List<String> answers;

    public Long getQuestionId() {
        return questionId;
    }

    public QuestionRelation setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public Map<Long, String> getQuestions() {
        return questions;
    }

    public QuestionRelation setQuestions(Map<Long, String> questions) {
        this.questions = questions;
        return this;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public QuestionRelation setAnswers(List<String> answers) {
        this.answers = answers;
        return this;
    }

    public String getText() {
        return text;
    }

    public QuestionRelation setText(String text) {
        this.text = text;
        return this;
    }
}
