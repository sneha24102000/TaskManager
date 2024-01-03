package com.nikozka.app.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @Size(min = 5, max = 255, message = "User name must be between 5 and 255 characters")
    private String username;
    @NotNull
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
}