package org.soippo.entity;

import org.soippo.utils.QuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @OneToMany
    @JoinTable(name = "answer2question",
            joinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")})
    @OrderColumn(name = "order")
    private List<Answer> answers;
}
