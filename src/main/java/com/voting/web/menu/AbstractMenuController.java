package com.voting.web.menu;

import com.voting.service.DishService;
import com.voting.service.LunchService;
import com.voting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMenuController {

    @Autowired
    private DishService dishService;

    @Autowired
    private LunchService lunchService;

    @Autowired
    private RestaurantService restaurantService;
}
