package com.voting.service;

import com.voting.DishTestData;
import com.voting.LunchTestData;
import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static com.voting.DishTestData.*;
import static com.voting.LunchTestData.NOT_FOUND;
import static com.voting.LunchTestData.getNew;
import static com.voting.LunchTestData.*;
import static com.voting.RestaurantTestData.FIRST_RESTAURANT;
import static com.voting.RestaurantTestData.FIRST_RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LunchServiceTest extends AbstractServiceTest {

    @Autowired
    private LunchService lunchService;

    @Test
    void delete() throws Exception {
        lunchService.delete(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID);
        assertThrows(NotFoundException.class, () -> lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID));
    }

    @Test
    void create() throws Exception {
        Lunch created = lunchService.create(getNew());
        int newId = created.getId();
        Lunch newLunch = getNew();
        newLunch.setId(newId);
        LUNCH_MATCHER.assertMatch(created, newLunch);
        LUNCH_MATCHER.assertMatch(lunchService.get(created.getRestaurant().getId(), newId), newLunch);
    }

    @Test
    void duplicateDateCreate() throws Exception {
        assertThrows(DataAccessException.class, () -> lunchService.create(
                new Lunch(null, LocalDate.parse("2020-08-30"), FIRST_LUNCH.getRestaurant())));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> lunchService.delete(FIRST_RESTAURANT_ID, NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        Lunch lunch = lunchService.get(FIRST_RESTAURANT_ID,FIRST_LUNCH_ID);
        LUNCH_MATCHER.assertMatch(lunch, FIRST_LUNCH);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> lunchService.get(FIRST_RESTAURANT_ID, NOT_FOUND));
    }

    @Test
    void update() throws Exception {
        Lunch updated = LunchTestData.getUpdated();
        lunchService.update(updated);
        DISH_MATCHER.assertMatch(lunchService.get(updated.getRestaurant().getId(), updated.getId()).getDishes(),
                FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH);
    }

    @Test
    void getAll() throws Exception {
        List<Lunch> all = lunchService.getAll();
        LUNCH_MATCHER.assertMatch(all, getAllSorted());
    }

    @Test
    void getByDate() throws Exception {
        List<Lunch> lunches = lunchService.getByDate(LocalDate.parse("2020-08-30"));
        LUNCH_MATCHER.assertMatch(lunches, FIRST_LUNCH);
    }

    @Test
    void getBetweenDates() throws Exception {
        List<Lunch> lunches = lunchService.getBetweenDates(LocalDate.parse("2020-08-31"), LocalDate.parse("2020-09-01"));
        LUNCH_MATCHER.assertMatch(lunches, SECOND_LUNCH, THIRD_LUNCH);
    }

    @Test
    void getByRestaurantIdAndDate() throws Exception {
        Lunch lunch = lunchService.getByRestaurantIdAndDate(100000, LocalDate.parse("2020-08-30"));
        LUNCH_MATCHER.assertMatch(lunch, FIRST_LUNCH);
    }

    @Test
    void addDishById() throws Exception {
        lunchService.addDishById(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID, NINTH_DISH_ID);
        Lunch lunch = lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID);
        DISH_MATCHER.assertMatch(lunch.getDishes(), FIRST_DISH, SECOND_DISH, THIRD_DISH, NINTH_DISH);
    }

    @Test
    void addDishThatNotExistById() throws Exception {
        assertThrows(DataAccessException.class,
                () -> lunchService.addDishById(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID, NOT_FOUND));
    }

    @Test
    void deleteDishById() throws Exception {
        lunchService.deleteDishById(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID, FIRST_DISH_ID);
        Lunch lunch = lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID);
        DISH_MATCHER.assertMatch(lunch.getDishes(), SECOND_DISH, THIRD_DISH);
    }

    @Test
    void deleteDishByNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> lunchService.deleteDishById(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID, NOT_FOUND));
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> lunchService.create(new Lunch(null,
                null, null)), ConstraintViolationException.class);
        validateRootCause(() -> lunchService.create(new Lunch(null,
                null, FIRST_RESTAURANT)), ConstraintViolationException.class);
        validateRootCause(() -> lunchService.create(new Lunch(null,
                LocalDate.of(2020, 8, 31), null)), ConstraintViolationException.class);
    }

    @Test
    void createLunchByRestaurantId() throws Exception {
        Lunch emptyNewLunch = getNewEmpty();
        Lunch created = lunchService.createLunchByRestaurantId(emptyNewLunch, FIRST_RESTAURANT_ID);
        emptyNewLunch.setId(created.getId());
        LUNCH_MATCHER.assertMatch(lunchService.get(FIRST_RESTAURANT_ID, created.getId()), emptyNewLunch);
    }

    @Test
    void checkLunchByRestaurantId() throws Exception {
        assertThrows(NotFoundException.class,
                () -> lunchService.checkLunchByRestaurantId(FIRST_RESTAURANT_ID, NOT_FOUND));
    }

    @Test
    void getAllLunchesForRestaurant() throws Exception {
        List<Lunch> all = lunchService.getAllLunchesForRestaurant(FIRST_RESTAURANT_ID);
        LUNCH_MATCHER.assertMatch(all, FIRST_LUNCH);
    }

    @Test
    void addDish() throws Exception {
        Dish newDish = DishTestData.getNew();
        Dish created = lunchService.addDish(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID, newDish);
        Lunch lunch = lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID);
        LUNCH_MATCHER.assertMatch(lunch, FIRST_LUNCH);
        newDish.setId(created.getId());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(lunch.getDishes(), FIRST_DISH, SECOND_DISH, THIRD_DISH, newDish);
    }
}
