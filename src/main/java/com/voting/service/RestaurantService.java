package com.voting.service;

import com.voting.model.Lunch;
import com.voting.model.Restaurant;
import com.voting.repository.RestaurantCrudRepository;
import com.voting.to.RestaurantTo;
import com.voting.util.ToUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantCrudRepository restaurantCrudRepository;

    private final LunchService  lunchService;

    public RestaurantService(RestaurantCrudRepository restaurantCrudRepository, LunchService lunchService) {
        this.restaurantCrudRepository = restaurantCrudRepository;
        this.lunchService = lunchService;
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

    public Restaurant getWithLunches(int id) {
        return checkNotFoundWithId(restaurantCrudRepository.getWithLunches(id), id);
    }

    public List<RestaurantTo> getAllWithLunchForDate(LocalDate date) {
        return ToUtil.LunchToRestaurantTo(lunchService.getByDate(date));
    }
}
