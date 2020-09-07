package com.voting.repository;

import com.voting.model.Lunch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LunchCrudRepository extends JpaRepository<Lunch, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Lunch l WHERE l.id=:id")
    int delete(@Param("id") int id);

    //EAGER associations cannot be fetched lazily, so we are using LAZY by default
    //Using EntityGraph to fetch restaurant and dishes
    //https://stackoverflow.com/questions/17847289/ignore-a-fetchtype-eager-in-a-relationship
    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Lunch> findById(int id);

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Lunch> findAll(Sort sort);

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Lunch> getByLunchDate(LocalDate date);

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT l from Lunch l WHERE l.lunchDate>= :startDate AND l.lunchDate< :endDate ORDER BY l.lunchDate DESC")
    List<Lunch> getBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT l from Lunch l WHERE l.lunchDate=:date AND l.restaurant.id=:id")
    Lunch getByRestaurantIdAndDate(@Param("id") int id, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query(value = "insert into DISHES_LUNCHES (DISH_ID, LUNCH_ID) VALUES (:dishId, :lunchId)", nativeQuery = true)
    void addDishById(@Param("lunchId") int id, @Param("dishId") int dishId);

    @Transactional
    @Modifying
    @Query(value = "delete from DISHES_LUNCHES WHERE LUNCH_ID=:lunchId AND DISH_ID=:dishId", nativeQuery = true)
    int deleteDishById(@Param("lunchId") int id, @Param("dishId") int dishId);
}
