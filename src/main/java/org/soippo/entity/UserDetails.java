package org.soippo.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class UserDetails extends org.springframework.security.core.userdetails.User implements Serializable {
    private User userData = new User();

    public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserDetails setUserData(User userData) {
        this.userData = userData;
        return this;
    }

    public Long getId() {
        return this.userData.getId();
    }
}
