package com.voting.web.lunch;

import com.voting.DishTestData;
import com.voting.LunchTestData;
import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.service.DishService;
import com.voting.service.LunchService;
import com.voting.util.exception.NotFoundException;
import com.voting.web.AbstractControllerTest;
import com.voting.web.json.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static com.voting.DishTestData.*;
import static com.voting.LunchTestData.*;
import static com.voting.RestaurantTestData.FIRST_RESTAURANT_ID;
import static com.voting.RestaurantTestData.SECOND_RESTAURANT_ID;
import static com.voting.TestUtil.readFromJson;
import static com.voting.TestUtil.userHttpBasic;
import static com.voting.UserTestData.ADMIN;
import static com.voting.UserTestData.USER;
import static com.voting.util.exception.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminLunchControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminLunchController.REST_URL + "/";

    @Autowired
    private LunchService lunchService;

    @Autowired
    private DishService dishService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID + "/lunches/" + FIRST_LUNCH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LUNCH_MATCHER_ID_DATE_DISHES.contentJson(FIRST_LUNCH));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "lunches")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LUNCH_MATCHER_ID_DATE_DISHES.contentJson(getAllSorted()));
    }

    @Test
    void getForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID + "/lunches")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LUNCH_MATCHER_ID_DATE_DISHES.contentJson(List.of(FIRST_LUNCH)));
    }

    @Test
    void createWithLocation() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + SECOND_RESTAURANT_ID + "/lunches")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());
        Lunch created = readFromJson(action, Lunch.class);
        Assertions.assertEquals(LocalDate.of(2020, 8, 30), created.getLunchDate());
        Assertions.assertNull(created.getDishes());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_RESTAURANT_ID + "/lunches/" + FIRST_LUNCH_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID));
    }

    @Test
    void addDishById() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_RESTAURANT_ID
                + "/lunches/" + FIRST_LUNCH_ID + "/dish/" + FOURTH_DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID).getDishes(),
                FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH);
    }

    @Test
    void addDishWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID
                + "/lunches/" + FIRST_LUNCH_ID + "/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(newId), newDish);
        DISH_MATCHER.assertMatch(lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID).getDishes(),
                FIRST_DISH, SECOND_DISH, THIRD_DISH, newDish);
    }

    @Test
    void deleteDishById() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_RESTAURANT_ID
                + "/lunches/" + FIRST_LUNCH_ID + "/dish/" + FIRST_DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(lunchService.get(FIRST_RESTAURANT_ID, FIRST_LUNCH_ID).getDishes(), SECOND_DISH, THIRD_DISH);
    }

    @Test
    void addDishWithLocationInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID
                + "/lunches/" + FIRST_LUNCH_ID + "/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewInvalid()))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "lunches"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "lunches")
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID
                + "/lunches/" + LunchTestData.NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
