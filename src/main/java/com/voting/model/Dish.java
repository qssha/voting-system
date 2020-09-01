package com.voting.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dishes_lunches",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "lunch_id"))
    List<Lunch> lunches;

    public Dish() {}

    public Dish(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Dish(String name, Integer price, List<Lunch> lunches) {
        this.name = name;
        this.price = price;
        this.lunches = lunches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Lunch> getLunches() {
        return lunches;
    }

    public void setLunches(List<Lunch> lunches) {
        this.lunches = lunches;
    }
}
