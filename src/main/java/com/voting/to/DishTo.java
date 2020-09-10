package com.voting.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Range(min = 5, max = 100000)
    private Integer price;

    public DishTo() {
    }

    public DishTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name, @NotNull @Range(min = 5, max = 100000) Integer price) {
        super(id);
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
