package com.voting.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lunches")
public class Lunch extends AbstractBaseEntity {

    @Column(name = "lunch_date")
    private LocalDateTime lunch_date;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "lunches", fetch = FetchType.EAGER)
    List<Dish> dishes;

    public LocalDateTime getLunch_date() {
        return lunch_date;
    }

    public void setLunch_date(LocalDateTime lunch_date) {
        this.lunch_date = lunch_date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
