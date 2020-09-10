package com.voting.web.lunch;

import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.service.LunchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractLunchController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LunchService lunchService;

    public List<Lunch> getAll() {
        log.info("getAll");
        return lunchService.getAll();
    }

    public List<Lunch> getForRestaurant(int restaurantId) {
        log.info("getAll for {}", restaurantId);
        return lunchService.getAllLunchesForRestaurant(restaurantId);
    }

    public Lunch get(int restaurantId, int id) {
        log.info("restaurant {} get {}", restaurantId, id);
        return lunchService.get(restaurantId, id);
    }

    public Lunch create(Lunch lunch, int restaurantId) {
        log.info("create {} restaurant {}", lunch, restaurantId);
        checkNew(lunch);
        return lunchService.createLunchByRestaurantId(lunch, restaurantId);
    }

    public void delete(int restaurantId, int id) {
        log.info("restaurant {} delete {}", restaurantId, id);
        lunchService.delete(restaurantId, id);
    }

    public void addDishById(int restaurantId, int id, int dishId) {
        log.info("restaurant {}, lunch {} add {}", restaurantId, id, dishId);
        lunchService.addDishById(restaurantId, id, dishId);
    }

    public void deleteDishById(int restaurantId, int id, int dishId) {
        log.info("for restaurant {}, lunch {} delete dish {}", restaurantId, id, dishId);
        lunchService.deleteDishById(restaurantId, id, dishId);
    }

    public Dish addDish(int restaurantId, int id, Dish dish) {
        log.info("restaurant {}, lunch {} add {}", restaurantId, id, dish);
        return lunchService.addDish(restaurantId, id, dish);
    }
}
