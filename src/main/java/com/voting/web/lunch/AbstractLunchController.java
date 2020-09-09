package com.voting.web.lunch;

import com.voting.model.Lunch;
import com.voting.service.LunchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractLunchController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LunchService lunchService;

    public List<Lunch> getAll() {
        log.info("getAll");
        return lunchService.getAll();
    }

    public Lunch get(int id) {
        log.info("get {}", id);
        return lunchService.get(id);
    }

    public Lunch create(Lunch lunch) {
        log.info("create {}", lunch);
        checkNew(lunch);
        return lunchService.create(lunch);
    }

    public void update(Lunch lunch, int id) {
        log.info("update {}", lunch);
        assureIdConsistent(lunch, id);
        lunchService.update(lunch);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        lunchService.delete(id);
    }

    public void addDishById(int id, int dishId) {
        log.info("to lunch {} add dish {}", id, dishId);
        lunchService.addDishById(id, dishId);
    }

    public void deleteDishById(int id, int dishId) {
        log.info("from lunch {} delete dish {}", id, dishId);
        lunchService.deleteDishById(id, dishId);
    }
}
