package com.nikozka.app.servises;

import com.nikozka.app.dtos.UserDto;
import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.exceptions.UserAlreadyExistException;
import com.nikozka.app.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Test
    void saveUserTestWhenNonExistingUsernameThenSaveUser() {
        UserDto userDto = new UserDto("newUser", "password");
        UserEntity userEntity = new UserEntity("newUser", "password");

        when(userRepository.findByUsername("newUser")).thenReturn(null);
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);

        userService.saveUser(userDto);

        verify(userRepository).saveAndFlush(userEntityCaptor.capture());
        assertEquals("newUser", userEntityCaptor.getValue().getUsername());
    }

    @Test
    void saveUserTestWhenExistingUsernameThanThrowException() {
        UserDto userDto = new UserDto("existingUser", "password");

        when(userRepository.findByUsername("existingUser")).thenReturn(new UserEntity());

        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(userDto));
    }
}
