package com.nikozka.app.controllers;

import com.nikozka.app.dtos.UserDto;
import com.nikozka.app.servises.SecurityUserDetailsService;
import com.nikozka.app.servises.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final SecurityUserDetailsService securityUserDetailsService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserDto userDto) {
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticate(@RequestBody @Valid UserDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        securityUserDetailsService.loadUserByUsername(request.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
