package org.soippo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany
    @JoinTable(name = "question2interview",
            joinColumns = {@JoinColumn(name = "interview_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")})
    @OrderColumn(name = "order")
   private List<Question> questions;
}
