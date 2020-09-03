package com.voting.service;

import com.voting.model.Restaurant;
import com.voting.repository.RestaurantCrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantCrudRepository restaurantCrudRepository;

    public RestaurantService(RestaurantCrudRepository restaurantCrudRepository) {
        this.restaurantCrudRepository = restaurantCrudRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must be not null");
        return restaurantCrudRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantCrudRepository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantCrudRepository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll() {
        return restaurantCrudRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must be not null");
        checkNotFoundWithId(restaurantCrudRepository.save(restaurant), restaurant.getId());
    }

    public Restaurant getWithLunches() {
        //TODO
        return null;
    }
}
