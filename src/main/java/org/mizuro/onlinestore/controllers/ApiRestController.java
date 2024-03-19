package org.mizuro.onlinestore.controllers;

import lombok.AllArgsConstructor;
import org.mizuro.onlinestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiRestController {

    private final ModelMapper modelMap;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    @GetMapping("/races")
    public String index() {
        logger.info("Hello World!");
        return "Hello World";
    }
}
