package org.soippo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "relation_question_table")
public class RelationAnswer {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "text")
    private String text;

    @Column(name = "answer")
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

    public RelationAnswer setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

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
