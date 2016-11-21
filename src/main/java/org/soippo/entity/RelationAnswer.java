package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;

@Entity
@Table(name = "relation_answers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationAnswer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Normal.class)
    private Long id;

//    @ManyToOne(targetEntity = QuestionRelation.class)
//    private QuestionRelation question;

    @Column(name = "questionId")
    @JsonView(View.Normal.class)
    private Long questionId;

    @Column(name = "text")
    @JsonView(View.Normal.class)
    private String text;

    @Column(name = "answer")
    @JsonView(View.Normal.class)
    private String answer;


    public Long getId() {
        return id;
    }

    public RelationAnswer setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuestionId() {
        return questionId;
    }
//    public RelationAnswer setQuestionId(Long questionId) {
//        this.questionId = questionId;
//        return this;
//    }

    public String getText() {
        return text;
    }

    public RelationAnswer setText(String text) {
        this.text = text;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public RelationAnswer setAnswer(String answer) {
        this.answer = answer;
        return this;
    }
}
