package me.learning.springboot.chapter1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HomeController {

    @GetMapping
    public String greeting(
            @RequestParam(name = "name", required = false) Optional<String> name) {
            return "Hey" + name.map(e -> " ," + e + "!").orElse("!");
    }
}