package org.soippo.service;

import org.soippo.repository.UserRepository;
import org.soippo.utils.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserAuthenticationService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            org.soippo.entity.User user = userRepository.findOne(Long.decode(username));
            if(user == null) {
                throw new UsernameNotFoundException("User not found!");
            }
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            return new User(username,
                    user.getPasswordHash(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(user.getRole())
            );
        } catch (UsernameNotFoundException ex) {
            throw ex;
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserRoles role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<UserRoles> getRoles(UserRoles role) {
        List<UserRoles> roles = new ArrayList<UserRoles>();

        if (role.equals(UserRoles.ADMINISTRATOR)) {
            roles.add(UserRoles.USER);
            roles.add(UserRoles.TEACHER);
            roles.add(UserRoles.ADMINISTRATOR);

        } else if (role.equals(UserRoles.TEACHER)) {
            roles.add(UserRoles.USER);
            roles.add(UserRoles.TEACHER);
        } else {
            roles.add(UserRoles.USER);
        }

        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<UserRoles> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (UserRoles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }


}
