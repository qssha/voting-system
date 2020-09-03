package com.voting.repository;

import com.voting.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface LunchCrudRepository extends JpaRepository<Lunch, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Lunch l WHERE l.id=:id")
    int delete(@Param("id") int id);

    List<Lunch> getByLunchDate(LocalDate date);

    @Query("SELECT l from Lunch l WHERE l.lunchDate>= :startDate AND l.lunchDate< :endDate ORDER BY l.lunchDate DESC")
    List<Lunch> getBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
