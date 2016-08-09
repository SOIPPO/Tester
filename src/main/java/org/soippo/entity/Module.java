package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "interview")
public class Module implements Serializable {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "module_id_sequence",
            allocationSize = 1,
            sequenceName = "module_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_sequence")
    @JsonProperty("id")
    private Long id;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Question.class,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "interview_id")
    @OrderColumn(name = "question_order")
    @OrderBy("question_order")
    @JsonProperty("questions")
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public Module setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
