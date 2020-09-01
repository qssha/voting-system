package com.voting.web;

import com.voting.model.Dish;
import com.voting.model.Lunch;
import com.voting.model.Restaurant;
import com.voting.repository.DishCrudRepository;
import com.voting.repository.LunchCrudRepository;
import com.voting.repository.RestaurantCrudRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    @Autowired
    RestaurantCrudRepository restaurantCrudRepository;

    @Autowired
    LunchCrudRepository lunchCrudRepository;

    @Autowired
    DishCrudRepository dishCrudRepository;

    @GetMapping("/")
    public @ResponseBody String root() {
        dishCrudRepository.save(new Dish(
                "Fourth", 1000, List.of(lunchCrudRepository.getOne(100002))));


        for (Lunch lunch :
                lunchCrudRepository.findAll()) {
            System.out.println(lunch.getRestaurant());
            System.out.println(lunch.getLunch_date());
            System.out.println(lunch.getDishes());
        }

        System.out.println(restaurantCrudRepository.findAll());

        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
