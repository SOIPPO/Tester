package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.soippo.utils.View;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "group_module")
public class GroupModules {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "group_modules_id_sequence",
            allocationSize = 1,
            sequenceName = "group_modules_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_modules_id_sequence")
    private Long id;

//    @Column(name = "group_id")
//    private Long groupId;
//
//    @Column(name = "module_id")
//    private Long moduleId;

    @Column(name = "begin_date")
    @JsonView(View.Normal.class)
    private LocalDate incomingDate;

    @Column(name = "end_date")
    @JsonView(View.Normal.class)
    private LocalDate finalDate;


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference(value = "module-group")
    private Group group;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    @JsonManagedReference(value = "group-module")
    private Module module;

    public Long getId() {
        return id;
    }

    public GroupModules setId(Long id) {
        this.id = id;
        return this;
    }

    @JsonView(View.Normal.class)
    public LocalDate getIncomingDate() {
        return incomingDate;
    }

    public GroupModules setIncomingDate(LocalDate beginDate) {
        this.incomingDate = beginDate;
        return this;
    }

    @JsonView(View.Normal.class)
    public LocalDate getFinalDate() {
        return finalDate;
    }

    public GroupModules setFinalDate(LocalDate endDate) {
        this.finalDate = endDate;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public GroupModules setGroup(Group group) {
        this.group = group;
        return this;
    }

    public Module getModule() {
        return module;
    }

    public GroupModules setModule(Module module) {
        this.module = module;
        return this;
    }
}
