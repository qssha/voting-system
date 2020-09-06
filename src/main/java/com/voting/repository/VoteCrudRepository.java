package com.voting.repository;

import com.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VoteCrudRepository extends JpaRepository<Vote, Integer> {
    Vote getByUserFKAndVoteDate(int userFK, LocalDate date);

    List<Vote> getAllByUserFK(int userFK);
}
