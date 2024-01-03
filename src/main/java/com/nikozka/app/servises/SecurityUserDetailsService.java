package com.nikozka.app.servises;

import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.exceptions.UserNotFoundException;
import com.nikozka.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (userEntity == null) {
            throw new UserNotFoundException("No user found with username: " + userName);
        }
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole())));
    }
}
