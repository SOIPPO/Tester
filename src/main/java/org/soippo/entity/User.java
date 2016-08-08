package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    @SequenceGenerator(name = "users_id_sequence",
            allocationSize = 1,
            sequenceName = "users_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_sequence")
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonProperty("middleName")
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    private String passwordHash;

    @JsonProperty("email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @JsonProperty("role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_module",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "interview_id", nullable = false, updatable = false)}
    )
    private List<Module> modules;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    @JsonManagedReference
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

    @JsonProperty("group")
    public Group getGroup() {
        return group;
    }

    @JsonProperty("groupId")
    public Long getGroupId() {
        return group.getId();
    }

    @JsonProperty("groupName")
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
