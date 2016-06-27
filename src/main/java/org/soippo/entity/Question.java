package org.soippo.entity;

import com.google.gson.annotations.SerializedName;
import org.soippo.utils.QuestionType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    @SerializedName("id")
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @SerializedName("type")
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "answer2question",
            joinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")})
    @OrderColumn(name = "order")
    @SerializedName("answers")
    private Set<Answer> answers;
}
