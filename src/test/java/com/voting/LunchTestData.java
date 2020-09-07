package com.voting;

import com.voting.model.Lunch;

import java.time.LocalDate;

import static com.voting.DishTestData.*;
import static com.voting.RestaurantTestData.*;
import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class LunchTestData {
    public static final TestMatcher<Lunch> LUNCH_MATCHER = TestMatcher.usingFieldsComparator(Lunch.class, "dishes");

    public static final int NOT_FOUND = 10;

    public static final int FIRST_LUNCH_ID = START_SEQ + 3;
    public static final int SECOND_LUNCH_ID = START_SEQ + 4;
    public static final int THIRD_LUNCH_ID = START_SEQ + 5;

    public static final Lunch FIRST_LUNCH = new Lunch(FIRST_LUNCH_ID,
            LocalDate.parse("2020-08-30"), FIRST_RESTAURANT, FIRST_DISH, SECOND_DISH, THIRD_DISH);
    public static final Lunch SECOND_LUNCH = new Lunch(SECOND_LUNCH_ID,
            LocalDate.parse("2020-08-31"), SECOND_RESTAURANT, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH);
    public static final Lunch THIRD_LUNCH = new Lunch(THIRD_LUNCH_ID,
            LocalDate.parse("2020-08-31"), THIRD_RESTAURANT, SEVENTH_DISH, EIGHTH_DISH, NINTH_DISH);

    public static Lunch getNew() {
        return new Lunch(null, LocalDate.parse("2020-09-01"), THIRD_RESTAURANT, SEVENTH_DISH, EIGHTH_DISH, NINTH_DISH);
    }

    public static Lunch getUpdated() {
        Lunch updated = new Lunch(FIRST_LUNCH);
        updated.getDishes().add(FOURTH_DISH);
        return updated;
    }
}
