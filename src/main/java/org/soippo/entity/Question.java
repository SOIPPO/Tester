package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.soippo.utils.QuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @SequenceGenerator(name = "question_id_sequence",
            allocationSize = 1,
            sequenceName = "question_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_sequence")
    private Long id;

    @Column(name = "text")
    @JsonProperty("text")
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JsonProperty("type")
    private QuestionType type;

    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Answer.class,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "question_id")
    @OrderBy("answer_order")
    @OrderColumn(name = "answer_order")
    @JsonProperty("answers")
    private List<Answer> answers;

    @Column(name = "question_order")
    @JsonProperty("questionOrder")
    private Long questionOrder;

    @Column(name = "interview_id")
    @JsonProperty("interviewId")
    @JoinColumn(name = "questions_interview_FK")
    private Long moduleId;

    public Long getOrder() {
        return questionOrder;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public QuestionType getType() {
        return type;
    }

    public Long getQuestionOrder() {
        return questionOrder;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long interviewId) {
        this.moduleId = interviewId;
    }

    public Question setId(Long id) {
        this.id = id;
        return this;
    }

    public Question setText(String text) {
        this.text = text;
        return this;
    }

    public Question setType(QuestionType type) {
        this.type = type;
        return this;
    }

    public Question setAnswers(List<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Question setQuestionOrder(Long questionOrder) {
        this.questionOrder = questionOrder;
        return this;
    }
}
