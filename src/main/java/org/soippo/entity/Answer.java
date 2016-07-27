package org.soippo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @SerializedName("id")
    @SequenceGenerator(name = "answers_id_sequence",
            allocationSize = 1,
            sequenceName = "answers_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_id_sequence")
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "answer_order")
    @SerializedName("answer_order")
    private Long answer_order;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Question.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    @SerializedName("question")
    private Question question;

    @Column(name = "is_correct")
    private Boolean isCorrect = Boolean.FALSE;

    public Long getOrder() {
        return answer_order;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getAnswer_order() {
        return answer_order;
    }

    public Question getQuestion() {
        return question;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }
}
