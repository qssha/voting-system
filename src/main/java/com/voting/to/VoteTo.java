package com.voting.to;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteTo extends BaseTo {

    private Integer restaurant_id;

    @NotNull
    private LocalDate voteDate;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restaurant_id, @NotNull LocalDate voteDate) {
        super(id);
        this.restaurant_id = restaurant_id;
        this.voteDate = voteDate;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }
}
