package me.learning.springboot.chapter1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChapterController {

    private final ChapterRepository chapterRepo;

    public ChapterController(ChapterRepository repository) {
        this.chapterRepo = repository;
    }

    @GetMapping("/chapters")
    public Flux<Chapter> listing() {
        return chapterRepo.findAll();
    }
}
