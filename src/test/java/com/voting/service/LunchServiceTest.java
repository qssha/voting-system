package com.voting.service;

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
        lunchService.delete(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> lunchService.get(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID));
    }

    @Test
    void create() throws Exception {
        Lunch created = lunchService.create(getNew());
        int newId = created.getId();
        Lunch newLunch = getNew();
        newLunch.setId(newId);
        LUNCH_MATCHER.assertMatch(created, newLunch);
        LUNCH_MATCHER.assertMatch(lunchService.get(newId, created.getRestaurant().getId()), newLunch);
    }

    @Test
    void duplicateDateCreate() throws Exception {
        assertThrows(DataAccessException.class, () -> lunchService.create(
                new Lunch(null, LocalDate.parse("2020-08-30"), FIRST_LUNCH.getRestaurant())));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> lunchService.delete(NOT_FOUND, FIRST_RESTAURANT_ID));
    }

    @Test
    void get() throws Exception {
        Lunch lunch = lunchService.get(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID);
        LUNCH_MATCHER.assertMatch(lunch, FIRST_LUNCH);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> lunchService.get(NOT_FOUND, FIRST_RESTAURANT_ID));
    }

    @Test
    void update() throws Exception {
        FIRST_LUNCH.getDishes().add(NINTH_DISH);
        lunchService.update(FIRST_LUNCH);
        DISH_MATCHER.assertMatch(lunchService.get(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID).getDishes(),
                FIRST_DISH, SECOND_DISH, THIRD_DISH, NINTH_DISH);
    }

    @Test
    void getAll() throws Exception {
        List<Lunch> all = lunchService.getAll();
        LUNCH_MATCHER.assertMatch(all, SECOND_LUNCH, THIRD_LUNCH, FIRST_LUNCH);
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
        lunchService.addDishById(FIRST_LUNCH_ID, NINTH_DISH_ID);
        Lunch lunch = lunchService.get(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID);
        DISH_MATCHER.assertMatch(lunch.getDishes(), FIRST_DISH, SECOND_DISH, THIRD_DISH, NINTH_DISH);
    }

    @Test
    void addDishThatNotExistById() throws Exception {
        assertThrows(DataAccessException.class, () -> lunchService.addDishById(FIRST_LUNCH_ID, NOT_FOUND));
    }

    @Test
    void deleteDishById() throws Exception {
        lunchService.deleteDishById(FIRST_LUNCH_ID, FIRST_DISH_ID);
        Lunch lunch = lunchService.get(FIRST_LUNCH_ID, FIRST_RESTAURANT_ID);
        DISH_MATCHER.assertMatch(lunch.getDishes(), SECOND_DISH, THIRD_DISH);
    }

    @Test
    void deleteDishByNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> lunchService.deleteDishById(FIRST_LUNCH_ID, NOT_FOUND));
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
}
