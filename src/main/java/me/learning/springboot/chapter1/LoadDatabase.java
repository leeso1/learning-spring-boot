package me.learning.springboot.chapter1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner init(ChapterRepository repository) {
        return args -> {
            Flux.just(
                    new Chapter("Chapter1 - Quick Start with Java"),
                    new Chapter("Chapter2 - Reactive Web with Spring Boot"),
                    new Chapter("... and more ~!"))
                    .flatMap(repository::save)
                    .subscribe(System.out::println);
        };
    }
}
