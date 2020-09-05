package com.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_datetime")
    private LocalDateTime voteDateTime;

    @Column(name = "user_id")
    private Integer userFK;

    @Column(name = "restaurant_id")
    private Integer restaurantFK;

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
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
}
