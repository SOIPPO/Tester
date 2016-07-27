package org.soippo.entity;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("id")
    private Long id;

    @Column(name = "title")
    @SerializedName("title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, targetEntity = Question.class)
    @JoinColumn(name = "interview_id")
    @OrderColumn(name = "question_order")
    @OrderBy("question_order")
    @SerializedName("questions")
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
