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

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    List<Lunch> lunches;

    public Dish() {
    }

    public Dish(Integer id, String name, Integer price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public Dish(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Dish(String name, Integer price, List<Lunch> lunches) {
        this.name = name;
        this.price = price;
        this.lunches = lunches;
    }

    public Dish(Dish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.price = dish.getPrice();
        this.lunches = dish.getLunches();
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
