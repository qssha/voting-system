package com.voting.web;

import com.voting.repository.DishCrudRepository;
import com.voting.repository.LunchCrudRepository;
import com.voting.repository.RestaurantCrudRepository;
import com.voting.repository.UserCrudRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    @Autowired
    RestaurantCrudRepository restaurantCrudRepository;

    @Autowired
    LunchCrudRepository lunchCrudRepository;

    @Autowired
    DishCrudRepository dishCrudRepository;

    @Autowired
    UserCrudRepository userCrudRepository;

    @GetMapping("/")
    public @ResponseBody String root() {
        System.out.println(dishCrudRepository.findAll());
        dishCrudRepository.deleteById(100005);
        System.out.println(dishCrudRepository.findAll());

        System.out.println(userCrudRepository.findAll());

        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
