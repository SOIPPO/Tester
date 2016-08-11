package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_modules",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "module_id"})})
public class UserModules {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @SequenceGenerator(name = "usermodules_id_sequence",
            allocationSize = 1,
            sequenceName = "usermodules_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usermodules_id_sequence")
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Long userId;

    @Column(name = "module_id")
    @JsonProperty("moduleId")
    private Long moduleId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Module.class)
    @JoinColumn(name = "module_id", insertable = false, updatable = false, referencedColumnName = "id", nullable = false)
    private Module module;

    public Long getId() {
        return id;
    }

    public UserModules setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserModules setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public UserModules setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserModules setUser(User user) {
        this.user = user;
        return this;
    }

    public Module getModule() {
        return module;
    }

    public UserModules setModule(Module module) {
        this.module = module;
        return this;
    }
}
