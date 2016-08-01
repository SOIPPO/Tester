package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private Long id;

    @Column(name = "text")
    @JsonProperty("text")
    private String text;

    @Column(name = "answer_order")
    @JsonProperty("answerOrder")
    private Long answerOrder;

    @Column(name = "question_id", insertable = false, updatable = false)
    @JsonProperty("questionId")
    private Long questionId;

    @Column(name = "is_correct")
    @JsonProperty("isCorrect")
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


    public Long getQuestionId() {
        return questionId;
    }

}
