package com.voting.service;

import com.voting.model.Dish;
import com.voting.repository.DishCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishCrudRepository dishCrudRepository;

    public DishService(DishCrudRepository dishCrudRepository) {
        this.dishCrudRepository = dishCrudRepository;
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must be not null");
        return dishCrudRepository.save(dish);
    }

    public void delete(int id) {
        checkNotFoundWithId(dishCrudRepository.delete(id) != 0, id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishCrudRepository.findById(id).orElse(null), id);
    }

    public List<Dish> getAll() {
        return dishCrudRepository.findAll();
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "dish must be not null");
        checkNotFoundWithId(dishCrudRepository.save(dish), dish.getId());
    }
}
