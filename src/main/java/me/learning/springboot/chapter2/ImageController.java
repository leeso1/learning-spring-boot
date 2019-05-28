package me.learning.springboot.chapter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ImageController {

    private static Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @GetMapping(value = "/images", produces = MediaType.APPLICATION_JSON_VALUE)
    Flux<Image> images() {
      LOG.info("");
      return Flux.just(
              new Image("1", "learning-spring-boot-cover.jpg"),
              new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
              new Image("3", "bazinga.png")
      );
    }
}
