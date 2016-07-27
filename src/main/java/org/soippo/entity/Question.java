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
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @SerializedName("type")
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Answer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    @OrderBy("answer_order")
    @OrderColumn(name = "answer_order")
    @SerializedName("answers")
    private List<Answer> answers;

    @Column(name = "question_order")
    @SerializedName("question_order")
    private Long question_order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Module.class)
    @JoinColumn(name = "interview_id")
    @SerializedName("module")
    private Module module;

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

    public Answer getCorrectAnswer() {
        return answers.stream().filter(Answer::getCorrect).findAny().get();
    }
}
