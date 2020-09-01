package com.voting.repository;

import com.voting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishCrudRepository extends JpaRepository<Dish, Integer> {
}
