package com.voting.repository;

import com.voting.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchCrudRepository extends JpaRepository<Lunch, Integer> {
}
