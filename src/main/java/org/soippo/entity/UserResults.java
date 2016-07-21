package org.soippo.entity;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
@Table(name = "user_results")
public class UserResults {
    @Id
    @Column(name = "id", nullable = false)
    @SerializedName("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id")
    @SerializedName("question_id")
    private Long questionId;
    @Column(name = "user_id")
    @SerializedName("user_id")
    private Long userId;
    @Column(name = "result")
    @SerializedName("result")
    private String text;

    public Long getQuestionId() {
        return questionId;
    }

    public UserResults setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserResults setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getText() {
        return text;
    }

    public UserResults setText(String text) {
        this.text = text;
        return this;
    }
}
