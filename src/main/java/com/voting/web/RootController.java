package com.voting.web;

import com.voting.service.UserService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public @ResponseBody
    String root() {
        System.out.println(userService.getAll());

        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
