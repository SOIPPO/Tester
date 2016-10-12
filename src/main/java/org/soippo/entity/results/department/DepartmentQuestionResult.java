package org.soippo.entity.results.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.entity.UserResults;
import org.soippo.utils.View;

public class DepartmentQuestionResult {
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long questionId;
    @JsonProperty("title")
    @JsonView(View.Simplified.class)
    private String questionTitle;
    @JsonProperty("total")
    @JsonView(View.Simplified.class)
    private Long total = 0L;
    @JsonProperty("correct")
    @JsonView(View.Simplified.class)
    private Long correct = 0L;

    public Long getQuestionId() {
        return questionId;
    }

    public DepartmentQuestionResult setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public DepartmentQuestionResult setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public DepartmentQuestionResult setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getCorrect() {
        return correct;
    }

    public DepartmentQuestionResult setCorrect(Long correct) {
        this.correct = correct;
        return this;
    }

    public DepartmentQuestionResult addUserResult(UserResults result) {
        correct += result.getIsCorrect() ? 1 : 0;
        total++;
        questionId = result.getQuestionId();
        questionTitle = result.getQuestion().getText();
        return this;
    }
}
