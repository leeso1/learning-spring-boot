package me.learning.springboot.chapter3;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface ImageRepository extends ReactiveCrudRepository<Image, String> {

  public Mono<Image> findByName(String name);
  
}
