package org.soippo.entity;


import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(name = "groups_id_sequence",
            allocationSize = 1,
            sequenceName = "groups_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_sequence")
    @Column(name = "id")
    @SerializedName("id")
    private Long id;

    @Column(name = "name")
    @SerializedName("name")
    private String name;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="group")
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
