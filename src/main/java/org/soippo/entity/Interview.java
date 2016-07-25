package org.soippo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "interview_id_sequence",
            allocationSize = 1,
            sequenceName = "interview_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_id_sequence")
    @SerializedName("id")
    @Expose
    private Long id;

    @Column(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, targetEntity = Question.class)
    @JoinColumn(name = "interview_id")
    @OrderColumn(name = "question_order")
    @OrderBy("question_order")
    @SerializedName("questions")
    @Expose
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public Interview setTitle(String title) {
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
