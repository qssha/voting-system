package com.voting.service;

import com.voting.model.Lunch;
import com.voting.repository.LunchCrudRepository;
import com.voting.to.RestaurantTo;
import com.voting.util.MenuUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.*;

@Service
public class LunchService {

    private final LunchCrudRepository lunchCrudRepository;

    private final RestaurantService restaurantService;

    public LunchService(LunchCrudRepository lunchCrudRepository, RestaurantService restaurantService) {
        this.lunchCrudRepository = lunchCrudRepository;
        this.restaurantService = restaurantService;
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public Lunch create(Lunch lunch) {
        Assert.notNull(lunch, "lunch must be not null");
        return lunchCrudRepository.save(lunch);
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithMsg(lunchCrudRepository.deleteByIdAndRestaurantId(id, restaurantId) != 0,
                String.format("Not found lunch id=%d for restaurant id=%d", id, restaurantId));
    }

    public Lunch get(int id, int restaurantId) {
        return checkNotFoundWithMsg(lunchCrudRepository.getByIdAndRestaurantId(id, restaurantId).orElse(null),
                String.format("Not found lunch id=%d for restaurant id=%d", id, restaurantId));
    }

    public List<Lunch> getAll() {
        return lunchCrudRepository.findAll(Sort.by(Sort.Direction.DESC, "lunchDate"));
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public void update(Lunch lunch) {
        Assert.notNull(lunch, "lunch must be not null");
        checkNotFoundWithId(lunchCrudRepository.save(lunch), lunch.getId());
    }

    @Cacheable("lunches")
    public List<Lunch> getByDate(LocalDate date) {
        return lunchCrudRepository.getByLunchDate(date);
    }

    public List<Lunch> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return lunchCrudRepository.getBetweenDates(startDate, endDate);
    }

    @Cacheable("lunches")
    public Lunch getByRestaurantIdAndDate(int id, LocalDate date) {
        return checkNotFoundWithId(lunchCrudRepository.getByRestaurantIdAndDate(id, date), id);
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public void addDishById(int id, int dishId) {
        lunchCrudRepository.addDishById(id, dishId);
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public void deleteDishById(int id, int dishId) {
        checkNotFound(lunchCrudRepository.deleteDishById(id, dishId) != 0, "Lunch id=" + id + ", Dish id=" + dishId);
    }

    @Transactional
    @CacheEvict(value = "lunches", allEntries = true)
    public Lunch createLunchByRestaurantId(Lunch lunch, int restaurantId) {
        Assert.notNull(lunch, "lunch must be not null");
        lunch.setRestaurant(restaurantService.get(restaurantId));
        return lunchCrudRepository.save(lunch);
    }

    public List<RestaurantTo> getAllWithLunchForDate(LocalDate date) {
        return MenuUtil.LunchToRestaurantTo(getByDate(date));
    }
}
