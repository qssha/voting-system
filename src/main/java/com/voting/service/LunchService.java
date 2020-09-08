package com.voting.service;

import com.voting.model.Lunch;
import com.voting.repository.LunchCrudRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.*;

@Service
public class LunchService {

    private final LunchCrudRepository lunchCrudRepository;

    public LunchService(LunchCrudRepository lunchCrudRepository) {
        this.lunchCrudRepository = lunchCrudRepository;
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public Lunch create(Lunch lunch) {
        Assert.notNull(lunch, "lunch must be not null");
        return lunchCrudRepository.save(lunch);
    }

    @CacheEvict(value = "lunches", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(lunchCrudRepository.delete(id) != 0, id);
    }

    public Lunch get(int id) {
        return checkNotFoundWithId(lunchCrudRepository.findById(id).orElse(null), id);
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
}
