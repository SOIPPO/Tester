package org.soippo.service;

import org.soippo.repository.UserRepository;
import org.soippo.utils.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserAuthenticationService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    private static List<GrantedAuthority> getGrantedAuthorities(List<UserRoles> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.soippo.entity.User user = userRepository.findOne(Long.decode(username));
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        user.setUserResults(new ArrayList<>());

        return new org.soippo.entity.UserDetails(String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getMiddleName()),
                user.getPasswordHash(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getRole())
        ).setUserData(user);

    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserRoles role) {
        return getGrantedAuthorities(getRoles(role));
    }

    private List<UserRoles> getRoles(UserRoles role) {
        List<UserRoles> roles = new ArrayList<>();

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


}
