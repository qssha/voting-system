package com.voting;

import com.voting.model.Restaurant;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "lunches");

    public static final int NOT_FOUND = 10;

    public static final int FIRST_RESTAURANT_ID = START_SEQ;
    public static final int SECOND_RESTAURANT_ID = START_SEQ + 1;
    public static final int THIRD_RESTAURANT_ID = START_SEQ + 2;

    public static final Restaurant FIRST_RESTAURANT = new Restaurant(FIRST_RESTAURANT_ID, "First restaurant");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(SECOND_RESTAURANT_ID, "Second restaurant");
    public static final Restaurant THIRD_RESTAURANT = new Restaurant(THIRD_RESTAURANT_ID, "Third restaurant");

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(FIRST_RESTAURANT);
        updated.setName("UpdatedName");
        return updated;
    }
}
