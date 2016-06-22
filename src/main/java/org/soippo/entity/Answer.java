package org.soippo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

//    @OneToMany
//    @JoinTable(name = "answer2question",
//            joinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")}
//    )
//    private List<Question> questions;
}
