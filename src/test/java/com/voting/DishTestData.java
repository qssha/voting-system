package com.voting;

import com.voting.model.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsComparator(Dish.class, "lunches");

    public static final int NOT_FOUND = 10;

    public static final int FIRST_DISH_ID = START_SEQ + 6;
    public static final int SECOND_DISH_ID = START_SEQ + 7;
    public static final int THIRD_DISH_ID = START_SEQ + 8;
    public static final int FOURTH_DISH_ID = START_SEQ + 9;
    public static final int FIFTH_DISH_ID = START_SEQ + 10;
    public static final int SIXTH_DISH_ID = START_SEQ + 11;
    public static final int SEVENTH_DISH_ID = START_SEQ + 12;
    public static final int EIGHTH_DISH_ID = START_SEQ + 13;
    public static final int NINTH_DISH_ID = START_SEQ + 14;

    public static final Dish FIRST_DISH = new Dish(FIRST_DISH_ID, "Eggs", 100);
    public static final Dish SECOND_DISH = new Dish(SECOND_DISH_ID, "Steak", 1500);
    public static final Dish THIRD_DISH = new Dish(THIRD_DISH_ID, "Cheesecake", 700);
    public static final Dish FOURTH_DISH = new Dish(FOURTH_DISH_ID, "Borscht", 700);
    public static final Dish FIFTH_DISH = new Dish(FIFTH_DISH_ID, "Spaghetti", 300);
    public static final Dish SIXTH_DISH = new Dish(SIXTH_DISH_ID, "Apple pie", 700);
    public static final Dish SEVENTH_DISH = new Dish(SEVENTH_DISH_ID, "Solyanka", 900);
    public static final Dish EIGHTH_DISH = new Dish(EIGHTH_DISH_ID, "Pelmeni", 600);
    public static final Dish NINTH_DISH = new Dish(NINTH_DISH_ID, "Pancakes", 500);

    public static Dish getNew() {
        return new Dish(null, "New dish", 1000);
    }

    public static Dish getUpdated() {
        Dish dish = new Dish(FIRST_DISH);
        dish.setName("UpdatedName");
        return dish;
    }

    public static List<Dish> getAllDishesSortedByName() {
        return Stream.of(FIRST_DISH, SECOND_DISH, THIRD_DISH, FOURTH_DISH, FIFTH_DISH, SIXTH_DISH, SEVENTH_DISH,
                EIGHTH_DISH, NINTH_DISH).sorted(Comparator.comparing(Dish::getName)).collect(Collectors.toList());
    }

    public static Dish getNewInvalid() {
        return new Dish("", 100);
    }

    public static Dish getUpdatedInvalid() {
        Dish dish = new Dish(FIRST_DISH);
        dish.setPrice(1);
        return dish;
    }
}
