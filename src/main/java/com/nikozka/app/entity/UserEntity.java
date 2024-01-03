package com.nikozka.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(unique = true)
    private String username;
    @Setter
    private String password;
    @Setter
    private String role = "ROLE_USER";

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
