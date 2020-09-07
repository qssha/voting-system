package com.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "user_id")
    private Integer userFK;

    @Column(name = "restaurant_id")
    private Integer restaurantFK;

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate voteDate;

    public Vote() {
    }

    public Vote(Integer id, Integer userFK, Integer restaurantFK) {
        super(id);
        this.userFK = userFK;
        this.restaurantFK = restaurantFK;
    }

    public Vote(Integer userFK, Integer restaurantFK) {
        this.userFK = userFK;
        this.restaurantFK = restaurantFK;
    }

    public Vote(Integer id, Integer userFK, Integer restaurantFK, LocalDate voteDate) {
        super(id);
        this.userFK = userFK;
        this.restaurantFK = restaurantFK;
        this.voteDate = voteDate;
    }

    public Vote(Integer userFK, Integer restaurantFK, LocalDate voteDate) {
        this.userFK = userFK;
        this.restaurantFK = restaurantFK;
        this.voteDate = voteDate;
    }

    public Integer getUserFK() {
        return userFK;
    }

    public void setUserFK(Integer userFK) {
        this.userFK = userFK;
    }

    public Integer getRestaurantFK() {
        return restaurantFK;
    }

    public void setRestaurantFK(Integer restaurantFK) {
        this.restaurantFK = restaurantFK;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDateTime) {
        this.voteDate = voteDateTime;
    }
}
