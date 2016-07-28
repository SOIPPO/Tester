package org.soippo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.SerializedName;
import org.soippo.serialization.UserDeserializer;
import org.soippo.utils.UserRoles;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@JsonDeserialize(using = UserDeserializer.class)
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @SerializedName("id")
    @SequenceGenerator(name = "users_id_sequence",
            allocationSize = 1,
            sequenceName = "users_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_sequence")
    private Long id;

    @SerializedName("firstName")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @SerializedName("lastName")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @SerializedName("middleName")
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "password", nullable = false)
    @SerializedName("password")
    private String passwordHash;

    @SerializedName("email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @SerializedName("role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_module",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "interview_id", nullable = false, updatable = false)}
    )
    private List<Module> modules;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    private List<UserResults> userResults;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String secondName) {
        this.lastName = secondName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public User setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public User setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRoles getRole() {
        return role;
    }

    public User setRole(UserRoles role) {
        this.role = role;
        return this;
    }

    @SerializedName("group")
    public Group getGroup() {
        return group;
    }

    @SerializedName("groupId")
    public Long getGroupId() {
        return group.getId();
    }

    @SerializedName("groupName")
    public String getGroupName() {
        return group.getName();
    }

    public User setGroup(Group group) {
        this.group = group;
        return this;
    }

    public List<UserResults> getUserResults() {
        return userResults;
    }

    public User setUserResults(List<UserResults> userResults) {
        this.userResults = userResults;
        return this;
    }
}
