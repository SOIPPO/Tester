package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "module")
@JsonIgnoreProperties(value = {"users", "userModules"})
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
    cascade = {CascadeType.ALL})
    @JoinColumn(name = "interview_id")
    @OrderColumn(name = "question_order")
    @OrderBy("question_order")
    @JsonProperty("questions")
    @JsonView(View.Normal.class)
    private List<Question> questions;


    @JsonProperty("relation_questions")
    @JsonView(View.Normal.class)
    private transient List<QuestionRelation> questionRelations;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module")
    @JsonIgnore
    @JsonView(View.Extended.class)
    private List<GroupModules> groupModules;

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

    public List<QuestionRelation> getQuestionRelations() {
        return questionRelations;
    }

    public Module setQuestionRelations(List<QuestionRelation> questionRelations) {
        this.questionRelations = questionRelations;
        return this;
    }
}
