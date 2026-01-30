package com.learn.boost.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String password_hash;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String dateOfBirth;
    private String created_at;
    private String updated_at;
}
