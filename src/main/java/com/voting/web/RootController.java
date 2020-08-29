package com.voting.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class RootController {
    private final static Logger log = getLogger(RootController.class);

    @GetMapping("/")
    public @ResponseBody String root() {
        log.debug("FIRST DEBUG");
        return "OK\n";
    }
}
