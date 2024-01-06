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
    @NotNull(message = "user.username.notNull")
    @Size(min = 5, max = 255, message = "user.username.size")
    private String username;
    @NotNull(message = "user.password.notNull")
    @Size(min = 8, max = 255, message = "user.password.size")
    private String password;
}