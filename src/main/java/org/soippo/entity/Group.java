package org.soippo.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group implements Serializable {
    @Id
    @SequenceGenerator(name = "groups_id_sequence",
            allocationSize = 1,
            sequenceName = "groups_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_sequence")
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="group")
    @JsonManagedReference
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
