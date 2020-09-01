package com.voting.repository;

import com.voting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserCrudRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}
