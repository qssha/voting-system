package com.voting.web;

import com.voting.repository.RestaurantCrudRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    @Autowired
    RestaurantCrudRepository crudRepository;

    @GetMapping("/")
    public @ResponseBody String root() {
        crudRepository.deleteAll();
        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
