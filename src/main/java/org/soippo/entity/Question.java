package org.soippo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.soippo.utils.QuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    @SerializedName("id")
    @SequenceGenerator(name = "question_id_sequnce",
            allocationSize = 1,
            sequenceName = "question_id_sequnce")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_sequnce")
    @Expose
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    @Expose
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @SerializedName("type")
    @Expose
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Answer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    @OrderBy("answer_order")
    @OrderColumn(name = "answer_order")
    @SerializedName("answers")
    @Expose
    private List<Answer> answers;

    @Column(name = "question_order")
    @SerializedName("question_order")
    @Expose
    private Long question_order;

    @Column(name = "interview_id")
    @SerializedName("interview_id")
    @JoinColumn(name = "questions_interview_FK")
    @Expose
    private Long interviewId;

    public Long getOrder() {
        return question_order;
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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Long getQuestion_order() {
        return question_order;
    }

    public Long getInterviewId() {
        return interviewId;
    }
}
