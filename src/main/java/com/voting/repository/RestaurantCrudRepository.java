package com.voting.repository;

import com.voting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantCrudRepository extends JpaRepository<Restaurant, Integer> {
}
