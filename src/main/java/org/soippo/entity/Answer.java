package org.soippo.entity;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "id")
    @SerializedName("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "answer_order")
    @SerializedName("answer_order")
    private Long answer_order;

    @Column(name = "question_id")
    @SerializedName("question_id")
    private Long questionId;

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

    public Long getQuestionId() {
        return questionId;
    }
}
