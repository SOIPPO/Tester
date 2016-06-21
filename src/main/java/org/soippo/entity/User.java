package org.soippo.entity;

import com.google.gson.annotations.SerializedName;
import org.soippo.utils.UserRoles;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @SerializedName("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String passwordHash;

    @SerializedName("email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @SerializedName("role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "group_id")
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @SerializedName("groupId")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
