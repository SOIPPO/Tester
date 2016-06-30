package org.soippo.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @SerializedName("type")
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Answer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    @OrderBy("answer_order")
    @SerializedName("answers")
    private List<Answer> answers;

    @Column(name = "question_order")
    @SerializedName("order")
    private Long order;

    @Column(name = "interview_id")
    @SerializedName("interview_id")
    @JoinColumn(name = "questions_interview_FK")
    private Long interviewId;

    public Long getOrder() {
        return order;
    }

}
