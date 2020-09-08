package com.voting.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "price")
    @NotNull
    @Range(min = 5, max = 100000)
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

    public Dish(Integer id, String name, Integer price, List<Lunch> lunches) {
        super(id);
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
