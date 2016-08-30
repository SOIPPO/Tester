package org.soippo.entity;


import com.fasterxml.jackson.annotation.*;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Group implements Serializable {
    @Id
    @SequenceGenerator(name = "groups_id_sequence",
            allocationSize = 1,
            sequenceName = "groups_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_sequence")
    @Column(name = "id")
    @JsonProperty("id")
    @JsonView(View.Simplified.class)
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    @JsonView(View.Simplified.class)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<User> users;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    @JsonView(View.Extended.class)
    private List<GroupModules> groupModules;

//    private transient List<Module> availableModules;
//    private transient LocalDate incomingInspectionDate;
//    private transient LocalDate finalInspectionDate;

    public Long getId() {
        return id;
    }

    public Group setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    @JsonIgnore
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<GroupModules> getGroupModules() {
        return groupModules;
    }

    public void setGroupModules(List<GroupModules> groupModules) {
        this.groupModules = groupModules;
    }

    @JsonProperty("modules")
    @JsonView(View.Normal.class)
    public List<Module> getAvailableModules() {
        return groupModules.stream().map(GroupModules::getModule).collect(Collectors.toList());
    }

    @JsonProperty("incoming_inspection_date")
    @JsonView(View.Normal.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    public LocalDate getIncomingInspectionDate() {
        return groupModules.stream().findAny().orElse(new GroupModules().setIncomingDate(LocalDate.now())).getIncomingDate();
    }

    @JsonProperty("final_inspection_date")
    @JsonView(View.Normal.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    public LocalDate getFinalInspectionDate() {
        return groupModules.stream().findAny().orElse(new GroupModules().setFinalDate(LocalDate.now())).getFinalDate();
    }
}
