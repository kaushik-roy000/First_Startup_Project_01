package com.learn.boost.repository;

import com.learn.boost.model.UserUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUPdateHistory extends JpaRepository<UserUpdateHistory,String> {
}
