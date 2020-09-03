package com.voting.service;

import com.voting.model.Lunch;
import com.voting.model.Restaurant;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.voting.LunchTestData.FIRST_LUNCH;
import static com.voting.LunchTestData.LUNCH_MATCHER;
import static com.voting.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void delete() throws Exception {
        restaurantService.delete(FIRST_RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(FIRST_RESTAURANT_ID));
    }

    @Test
    void create() throws Exception {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.getId();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class,
                () -> restaurantService.create(new Restaurant(null, "First restaurant")));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = restaurantService.get(FIRST_RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, FIRST_RESTAURANT);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> restaurantService.get(NOT_FOUND));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(FIRST_RESTAURANT_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(all, FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void getWithLunches() throws Exception {
        Restaurant restaurant = restaurantService.getWithLunches(FIRST_RESTAURANT_ID);
        List<Lunch> lunches = restaurant.getLunches();
        LUNCH_MATCHER.assertMatch(lunches, FIRST_LUNCH);
    }
}
