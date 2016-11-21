package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "relation_questions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Normal.class)
    @Column(name = "id")
    private Long questionId;

    @Column(name = "text")
    @JsonView(View.Normal.class)
    private String text;

    @JsonView(View.Extended.class)
//    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "questionId")
    private List<RelationAnswer> relationAnswers;

    @Column(name = "moduleId")
    private Long moduleId;

    public List<RelationAnswer> getRelationAnswers() {
        return relationAnswers;
    }

    public QuestionRelation setRelationAnswers(List<RelationAnswer> relationAnswers) {
        this.relationAnswers = relationAnswers;
        return this;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public QuestionRelation setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public QuestionRelation setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getText() {
        return text;
    }

    public QuestionRelation setText(String text) {
        this.text = text;
        return this;
    }

    @JsonProperty(value = "questions", access = JsonProperty.Access.READ_ONLY)
    @JsonView(View.Normal.class)
    public List<String> getQuestions() {
        List<String> result = relationAnswers.stream().filter(Objects::nonNull).map(RelationAnswer::getText).collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }

    @JsonProperty(value = "answers", access = JsonProperty.Access.READ_ONLY)
    @JsonView(View.Normal.class)
    public List<String> getAnswers() {
        List<String> result = relationAnswers.stream().filter(Objects::nonNull).map(RelationAnswer::getAnswer).collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }

    @JsonIgnore
    public void setQuestions() {
    }

    @JsonIgnore
    public void setAnswers() {
    }

}
