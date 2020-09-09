package com.voting.util;

import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.to.DishTo;
import com.voting.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class ToUtil {
    public static List<RestaurantTo> LunchToRestaurantTo(List<Lunch> lunches) {
        return lunches.stream().map(x -> new RestaurantTo(x.getRestaurant().getId(), x.getRestaurant().getName(),
                dishToDishTo(x.getDishes()))).collect(Collectors.toList());
    }

    public static List<DishTo> dishToDishTo(List<Dish> dishes) {
        return dishes.stream().map(x -> new DishTo(x.getId(), x.getName(), x.getPrice())).collect(Collectors.toList());
    }
}
