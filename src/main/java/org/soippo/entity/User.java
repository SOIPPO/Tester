package org.soippo.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.soippo.serialization.UserDeserializer;
import org.soippo.utils.UserRoles;
import org.soippo.utils.View;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@JsonDeserialize(using = UserDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "userModules"})
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    @SequenceGenerator(name = "users_id_sequence",
            allocationSize = 1,
            sequenceName = "users_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_sequence")
    @JsonView(View.Simplified.class)
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "first_name", nullable = false)
    @JsonView(View.Simplified.class)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name", nullable = false)
    @JsonView(View.Simplified.class)
    private String lastName;

    @JsonProperty("middleName")
    @Column(name = "middle_name", nullable = false)
    @JsonView(View.Simplified.class)
    private String middleName;

    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    @JsonView(View.Simplified.class)
    private String passwordHash;

    @JsonProperty("email")
    @Column(name = "email", unique = true, nullable = false)
    @JsonView(View.Simplified.class)
    private String email;

    @JsonProperty("role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonView(View.Normal.class)
    private UserRoles role;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private List<UserModules> userModules = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "userId")
    @JsonManagedReference
    private List<UserResults> userResults;
    private transient List<Module> modules = new ArrayList<>();


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
    @JsonView(View.Simplified.class)
    public Group getGroup() {
        return group;
    }

    public User setGroup(Group group) {
        this.group = group;
        return this;
    }

    @JsonProperty("groupId")
    @JsonView(View.Simplified.class)
    public Long getGroupId() {
        return group.getId();
    }

    @JsonProperty("groupName")
    @JsonView(View.Simplified.class)
    public String getGroupName() {
        return group.getName();
    }

    @JsonView(View.Normal.class)
    public List<UserResults> getUserResults() {
        return userResults;
    }

    public User setUserResults(List<UserResults> userResults) {
        this.userResults = userResults;
        return this;
    }

    @JsonProperty("modules")
    @JsonView(View.Normal.class)
    public List<Module> getModules() {
        if (modules.isEmpty() && !userModules.isEmpty()) {
            modules = userModules.stream().map(UserModules::getModule).collect(Collectors.toList());
        }
        return modules;
    }

    public User setModules(List<Module> modules) {
        this.modules = modules;
        this.userModules = modules.stream()
                .map(item -> new UserModules().setModule(item).setUser(this))
                .collect(Collectors.toList());
        return this;
    }

    public List<UserModules> getUserModules() {
        return userModules;
    }

    public User setUserModules(List<UserModules> userModules) {
        this.userModules = userModules;
        return this;
    }
}
