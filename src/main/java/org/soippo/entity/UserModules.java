package org.soippo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "user_module")
public class UserModules {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "users_modules_id_sequence",
            allocationSize = 1,
            sequenceName = "users_modules_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_modules_id_sequence")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "module_id")
    private Long moduleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Module module;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonBackReference
    private transient User user;

    public Module getModule() {
        return module;
    }

    public UserModules setModule(Module module) {
        this.moduleId = module.getId();
        this.module = module;
        return this;
    }

    public UserModules setUser(User user) {
        this.userId = user.getId();
        return this;
    }
}
