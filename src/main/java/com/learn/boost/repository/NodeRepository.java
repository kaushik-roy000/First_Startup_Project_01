package com.learn.boost.repository;

import com.learn.boost.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Notes,String> {
    List<Notes> findByUser_UserId(String userId);
}
