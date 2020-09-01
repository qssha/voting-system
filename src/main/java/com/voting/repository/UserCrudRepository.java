package com.voting.repository;

import com.voting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCrudRepository extends JpaRepository<User, Integer> {
}
