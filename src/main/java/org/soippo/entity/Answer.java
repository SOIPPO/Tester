package org.soippo.entity;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "id")
    @SerializedName("id")
    private Long id;

    @Column(name = "text")
    @SerializedName("text")
    private String text;

    @Column(name = "order")
    private Long order;

    @Column(name = "question_id")
    private Long questionId;

    public Long getOrder() {
        return order;
    }
}
