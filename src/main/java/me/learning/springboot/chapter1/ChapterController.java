package me.learning.springboot.chapter1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChapterController {

    @Autowired
    private ChapterRepository chapterRepo;

    @GetMapping("/chapters")
    public Flux<Chapter> listing() {
        return chapterRepo.findAll();
    }
}
