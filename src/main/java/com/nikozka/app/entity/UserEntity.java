package com.nikozka.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    @Column(unique = true)
    private String username;
    @Setter
    @Getter
    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
