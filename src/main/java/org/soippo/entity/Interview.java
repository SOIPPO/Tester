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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Question.class, mappedBy = "interviewId")
    @OrderColumn(name = "order")
    @OrderBy("order")
    @SerializedName("questions")
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

//    public List<Question> getAnswers() {
//        Comparator<Question> questionComparator = (o1, o2) -> Long.compare(o1.getOrder(), o2.getOrder());
//        return questions.stream().sorted(questionComparator).collect(Collectors.toList());
//    }
//
//    public void setQuestions(Set<Question> questions) {
//        this.questions = questions;
//    }
}
