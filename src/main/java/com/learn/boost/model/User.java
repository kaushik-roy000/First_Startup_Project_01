package com.learn.boost.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password_hash;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private int age;

    private String created_at;

    @ElementCollection
    private List<String> updated_at;
}
