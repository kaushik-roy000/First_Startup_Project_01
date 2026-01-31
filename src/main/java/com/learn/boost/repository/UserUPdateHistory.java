package com.learn.boost.repository;

import com.learn.boost.enums.UserUpdateField;
import com.learn.boost.model.UserUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserUPdateHistory extends JpaRepository<UserUpdateHistory,String> {
    // Get all history for a user
    @Query("SELECT h FROM UserUpdateHistory h WHERE h.user.userId = :userId")
    List<UserUpdateHistory> findAllByUserId(@Param("userId") String userId);

    // Get history for a user in a date range
    @Query("SELECT h FROM UserUpdateHistory h WHERE h.user.userId = :userId AND h.updated_at BETWEEN :start AND :end")
    List<UserUpdateHistory> findByUserIdAndDateRange(
            @Param("userId") String userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // Get history for a user and a specific field
    @Query("SELECT h FROM UserUpdateHistory h WHERE h.user.userId = :userId AND h.updatedField = :field")
    List<UserUpdateHistory> findByUserIdAndField(
            @Param("userId") String userId,
            @Param("field") UserUpdateField field
    );

    // Get history for a user, field, and date range
    @Query("SELECT h FROM UserUpdateHistory h WHERE h.user.userId = :userId AND h.updatedField = :field AND h.updated_at BETWEEN :start AND :end")
    List<UserUpdateHistory> findByUserIdAndFieldAndDateRange(
            @Param("userId") String userId,
            @Param("field") UserUpdateField field,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
