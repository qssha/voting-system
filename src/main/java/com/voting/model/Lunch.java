package com.voting.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "lunches")
public class Lunch extends AbstractBaseEntity {

    @Column(name = "lunch_date")
    private LocalDate lunchDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dishes_lunches",
            joinColumns = @JoinColumn(name = "lunch_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    List<Dish> dishes;

    public Lunch() {
    }

    public Lunch(Integer id, LocalDate lunchDate, Restaurant restaurant) {
        super(id);
        this.lunchDate = lunchDate;
        this.restaurant = restaurant;
    }

    public Lunch(Integer id, LocalDate lunchDate, Restaurant restaurant, Dish... dishes) {
        super(id);
        this.lunchDate = lunchDate;
        this.restaurant = restaurant;
        this.dishes = new ArrayList<>(Arrays.asList(dishes));
    }

    public Lunch(Lunch lunch) {
        this.id = lunch.getId();
        this.lunchDate = lunch.getLunchDate();
        this.restaurant = lunch.getRestaurant();
        this.dishes = lunch.getDishes();
    }

    public LocalDate getLunchDate() {
        return lunchDate;
    }

    public void setLunchDate(LocalDate lunch_date) {
        this.lunchDate = lunch_date;
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

    @Override
    public String toString() {
        return "Lunch{" +
                "lunch_date=" + lunchDate +
                ", restaurant=" + restaurant +
                ", dishes=" + dishes +
                '}';
    }
}
