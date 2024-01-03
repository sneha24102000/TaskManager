package com.nikozka.app.servises;

import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.exceptions.UserNotFoundException;
import com.nikozka.app.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityUserDetailsService userDetailsService;

    @Test
    void loadUserByUsernameTestWhenExistingUserThenReturnUserDetails() {
        UserEntity userEntity = new UserEntity("testUser", "testPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertAll("UserDetails",
                () -> assertNotNull(userDetails, "UserDetails should not be null"),
                () -> assertEquals("testUser", userDetails.getUsername(), "Username should match"),
                () -> assertEquals("testPassword", userDetails.getPassword(), "Password should match"),
                () -> assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")),
                        "Should have \"ROLE_USER\" authority")
        );
    }

    @Test
    void loadUserByUsernameTestWhenNonExistingUserThenThrowException() {
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonExistingUser"));
    }
}