package com.voting.web.restaurant;

import com.voting.model.Restaurant;
import com.voting.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return restaurantService.getWithLunches(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }
}
