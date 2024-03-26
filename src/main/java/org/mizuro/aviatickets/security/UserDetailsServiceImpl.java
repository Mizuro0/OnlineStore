package org.mizuro.aviatickets.security;

import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByUsername(username).orElseGet(() -> userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found")));

        return UserDetailsImpl.build(userEntity);
    }
}
