package org.soippo.entity;

import com.google.gson.annotations.SerializedName;
import org.soippo.utils.QuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question{
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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Answer.class, mappedBy = "questionId")
    @OrderColumn(name = "order")
    @OrderBy("order")
    @SerializedName("answers")
    private List<Answer> answers;

    @Column(name = "order")
    @SerializedName("order")
    private Long order;

    @Column(name = "interview_id")
    @SerializedName("interview_id")
    private Long interviewId;

    public Long getOrder() {
        return order;
    }

//    public List<Answer> getAnswers() {
//        Comparator<Answer> answerComparator = (o1, o2) -> Long.compare(o1.getOrder(), o2.getOrder());
//        return answers.stream().sorted(answerComparator).collect(Collectors.toList());
//    }
//
//    public void setAnswers(Set<Answer> answers) {
//        this.answers = answers;
//    }
}
