package com.learn.boost.repository;

import com.learn.boost.model.QuestionFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionFileRepository extends JpaRepository<QuestionFile,Integer> {
    List<QuestionFile> findByUserId(String userId);
    Optional<QuestionFile> findByNoteId(String noteId);
}
