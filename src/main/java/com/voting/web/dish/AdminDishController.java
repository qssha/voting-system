package com.voting.web.dish;

import com.voting.model.Dish;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController extends AbstractDishController {

    public static final String REST_URL = "/rest/admin/dishes";

    @Override
    @GetMapping
    public List<Dish> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return super.get(id);
    }

/*    @Override
    public Dish create(Dish dish) {
        return super.create(dish);
    }

    @Override
    public void update(Dish dish, int id) {
        super.update(dish, id);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }*/
}
