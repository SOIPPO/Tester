package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "user_results",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "question_id", "date"})}
)
public class UserResults implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    @SequenceGenerator(name = "userresults_id_sequence",
            allocationSize = 1,
            sequenceName = "userresults_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userresults_id_sequence")
    private Long id;

    @Column(name = "question_id")
    @JsonProperty("questionId")
    private Long questionId;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Long userId;

    @Column(name = "result")
    @JsonProperty("result")
    @JsonView(View.Normal.class)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Question.class)
    @JoinColumn(name = "question_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private Question question;

    @Column(name = "is_correct")
    @JsonProperty("isCorrect")
    private Boolean isCorrect;

    @Column(name = "date")
    @JsonProperty("date")
    private Date date;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public UserResults setIsCorrect(Boolean correct) {
        this.isCorrect = correct;
        return this;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public UserResults setDate(Date date) {
        this.date = date;
        return this;
    }

    public Long getModuleId() {
        return question.getInterviewId();
    }
}
