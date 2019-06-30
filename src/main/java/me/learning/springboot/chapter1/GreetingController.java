package me.learning.springboot.chapter1;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping
    public String greeting(
            @RequestParam(name = "name", required = false) Optional<String> name) {
        return "Hey" + name.map(e -> " ," + e + "!").orElse("!");
    }
}
