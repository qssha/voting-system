package com.voting.web.dish;

import com.voting.model.Dish;
import com.voting.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractDishController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    public List<Dish> getAll() {
        log.info("getAll");
        return dishService.getAll();
    }

    public Dish get(int id) {
        log.info("get {}", id);
        return dishService.get(id);
    }

    public Dish create(Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        return dishService.create(dish);
    }

    public void update(Dish dish, int id) {
        log.info("update {}", dish);
        assureIdConsistent(dish, id);
        dishService.update(dish);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        dishService.delete(id);
    }
}
