package com.voting.web.lunch;

import com.voting.model.Lunch;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminLunchController extends AbstractLunchController {

    public static final String REST_URL = "/rest/admin/restaurants";

    @GetMapping("/lunches")
    public List<Lunch> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{restaurantId}/lunches/{id}")
    public Lunch get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

/*    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lunch> createWithLocation(@Valid @RequestBody Lunch lunch) {
        Lunch created = super.create(lunch);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Lunch lunch, @PathVariable int id) {
        super.update(lunch, id);
    }*/

    @Override
    @DeleteMapping("/{restaurantId}/lunches/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        super.delete(restaurantId, id);
    }

/*    @Override
    @PutMapping("/{id}/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDishById(@PathVariable int id, @PathVariable int dishId) {
        super.addDishById(id, dishId);
    }

    @Override
    @DeleteMapping("/{id}/dish/{dishId}")
    public void deleteDishById(@PathVariable int id, @PathVariable int dishId) {
        super.deleteDishById(id, dishId);
    }*/
}
