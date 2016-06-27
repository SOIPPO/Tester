package org.soippo.entity;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("id")
    private Long id;

    @Column(name = "title")
    @SerializedName("title")
    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question2interview",
            joinColumns = {@JoinColumn(name = "interview_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")})
    @OrderColumn(name = "order")
    @SerializedName("questions")
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public Interview setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Interview setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Long getId() {
        return id;
    }
}
