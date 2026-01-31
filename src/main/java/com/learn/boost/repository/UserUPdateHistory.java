package com.learn.boost.repository;

import com.learn.boost.model.UserUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUPdateHistory extends JpaRepository<UserUpdateHistory,String> {
}
