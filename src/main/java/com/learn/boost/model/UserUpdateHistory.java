package com.learn.boost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.boost.enums.UserUpdateField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_update_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String update_id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;

    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)//always use string never use ordinal
    private UserUpdateField updatedField;
    private String old_value;
}
