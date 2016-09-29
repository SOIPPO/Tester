package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @JsonProperty("id")
    @SequenceGenerator(name = "answers_id_sequence",
            allocationSize = 1,
            sequenceName = "answers_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_id_sequence")
    @JsonView(View.Normal.class)
    private Long id;

    @Column(name = "text")
    @JsonProperty("text")
    @JsonView(View.Simplified.class)
    private String text;

    @Column(name = "answer_order")
    @JsonProperty("answerOrder")
    @JsonView(View.Normal.class)
    private Long answerOrder;

    @Column(name = "question_id", insertable = false, updatable = false)
    @JsonProperty("questionId")
    @JsonView(View.Normal.class)
    private Long questionId;

    @Column(name = "is_correct")
    @JsonProperty("isCorrect")
    @JsonView(View.Extended.class)
    private Boolean isCorrect = Boolean.FALSE;

    public Long getOrder() {
        return answerOrder;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Answer setText(String text) {
        this.text = text;
        return this;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public Answer setCorrect(Boolean correct) {
        isCorrect = correct;
        return this;
    }

    public Answer setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAnswerOrder() {
        return answerOrder;
    }

    public Answer setAnswerOrder(Long answerOrder) {
        this.answerOrder = answerOrder;
        return this;
    }

    public Answer setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }
}
