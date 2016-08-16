package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "module")
public class Module implements Serializable {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "module_id_sequence",
            allocationSize = 1,
            sequenceName = "module_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_sequence")
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long id;

    @Column(name = "title")
    @JsonProperty("title")
    @JsonView(View.Simplified.class)
    private String title;

    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = Question.class,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "interview_id")
    @OrderColumn(name = "question_order")
    @OrderBy("question_order")
    @JsonProperty("questions")
    private List<Question> questions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_module",
            joinColumns = { @JoinColumn(name = "modules_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;

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

    public Module setId(Long id) {
        this.id = id;
        return this;
    }

    public Module setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }
}
