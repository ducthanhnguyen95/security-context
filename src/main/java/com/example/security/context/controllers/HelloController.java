package com.example.security.context.controllers;

import com.example.security.context.services.AsyncService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger log = LogManager.getLogger(HelloController.class);

    private final AsyncService asyncService;

    public HelloController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/hello")
    public String hello(Authentication a) {
        return "Hello, " + a.getName() + "!";
    }

    @GetMapping("/bye")
    @Async
    public void goodbye() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        // do something with the username
    }

    @GetMapping("/async")
    public Object standardProcessing() throws Exception {
        log.info("Outside the @Async logic - before the async call: "
                + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        asyncService.asyncCall();

        log.info("Inside the @Async logic - after the async call: "
                + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
