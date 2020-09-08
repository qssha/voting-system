package com.voting.web;

import com.voting.service.LunchService;
import com.voting.service.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    private final UserService userService;
    private final LunchService lunchService;

    public RootController(UserService userService, LunchService lunchService) {
        this.userService = userService;
        this.lunchService = lunchService;
    }

    @GetMapping("/")
    public @ResponseBody
    String root() {
        System.out.println(lunchService.getByDate(LocalDate.of(2020, 8, 31)));
        System.out.println(lunchService.getByDate(LocalDate.of(2020, 8, 31)));
        System.out.println(lunchService.getByDate(LocalDate.of(2020, 8, 31)));
        System.out.println(lunchService.getByDate(LocalDate.of(2020, 8, 30)));
        System.out.println(lunchService.getByDate(LocalDate.of(2020, 8, 30)));


        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
