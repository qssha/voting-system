package com.voting.web.lunch;

import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.web.dish.AdminDishController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminLunchController extends AbstractLunchController {

    private final Clock clock;

    public static final String REST_URL = "/rest/admin/restaurants";

    public AdminLunchController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/lunches")
    public List<Lunch> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{restaurantId}/lunches")
    public List<Lunch> getForRestaurant(@PathVariable int restaurantId) {
        return super.getForRestaurant(restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/lunches/{id}")
    public Lunch get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @PostMapping(value = "/{restaurantId}/lunches")
    public ResponseEntity<Lunch> createWithLocation(@PathVariable int restaurantId) {
        Lunch created = super.create(new Lunch(LocalDate.now(clock)), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/lunches/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{restaurantId}/lunches/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        super.delete(restaurantId, id);
    }

    @PutMapping("/{restaurantId}/lunches/{id}/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDishById(@PathVariable int restaurantId, @PathVariable int id, @PathVariable int dishId) {
        super.addDishById(restaurantId, id, dishId);
    }

    @PostMapping(value = "/{restaurantId}/lunches/{id}/dish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> addDishWithLocation(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody Dish dish) {
        Dish created = super.addDish(restaurantId, id, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(AdminDishController.REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurantId}/lunches/{id}/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDishById(@PathVariable int restaurantId, @PathVariable int id, @PathVariable int dishId) {
        super.deleteDishById(restaurantId, id, dishId);
    }
}
