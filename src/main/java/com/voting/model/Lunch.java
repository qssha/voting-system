package com.voting.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lunches")
public class Lunch extends AbstractBaseEntity {

    @Column(name = "lunch_date")
    private LocalDate lunch_date;

    @Column(name = "rating")
    private Integer rating = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "lunches", fetch = FetchType.EAGER)
    List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lunch")
    List<User> users;

    public Lunch() {
    }

    public Lunch(Integer id, LocalDate lunch_date, Restaurant restaurant) {
        super(id);
        this.lunch_date = lunch_date;
        this.restaurant = restaurant;
    }

    public Lunch(Lunch lunch) {
        this.id = lunch.getId();
        this.lunch_date = lunch.getLunch_date();
        this.rating = lunch.getRating();
        this.restaurant = lunch.getRestaurant();
        this.dishes = lunch.getDishes();
        this.users = lunch.getUsers();
    }

    public LocalDate getLunch_date() {
        return lunch_date;
    }

    public void setLunch_date(LocalDate lunch_date) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "lunch_date=" + lunch_date +
                ", rating=" + rating +
                ", restaurant=" + restaurant +
                ", dishes=" + dishes +
                '}';
    }
}
