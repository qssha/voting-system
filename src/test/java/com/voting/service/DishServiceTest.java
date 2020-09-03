package com.voting.service;

import com.voting.model.Dish;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static com.voting.DishTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void delete() throws Exception {
        dishService.delete(FIRST_DISH_ID);
        assertThrows(NotFoundException.class, () -> dishService.get(FIRST_DISH_ID));
    }

    @Test
    void create() throws Exception {
        Dish created = dishService.create(getNew());
        int newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(newId), newDish);
    }

    @Test
    void duplicateDishCreate() throws Exception {
        assertThrows(DataAccessException.class, () -> dishService.create(new Dish(null, "Eggs", 100)));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> dishService.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        Dish dish = dishService.get(FIRST_DISH_ID);
        DISH_MATCHER.assertMatch(dish, FIRST_DISH);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> dishService.get(NOT_FOUND));
    }

    @Test
    void getAll() throws Exception {
        List<Dish> all = getAllDishesSortedByName();
        DISH_MATCHER.assertMatch(dishService.getAll(), all);
    }
}
