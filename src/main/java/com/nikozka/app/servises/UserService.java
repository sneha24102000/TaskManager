package com.nikozka.app.servises;

import com.nikozka.app.dtos.UserDto;
import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.exceptions.UserAlreadyExistException;
import com.nikozka.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void saveUser(UserDto userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername()) != null){
            throw new UserAlreadyExistException("A user with the specified username already exists");
        }
        userRepository.saveAndFlush(convertToEntity(userDTO));
    }

    private UserEntity convertToEntity(UserDto user) {
        return modelMapper.map(user, UserEntity.class);
    }
}
