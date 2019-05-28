package me.learning.springboot.chapter3;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "images")
public class Image {
  
  @Id
  private final String id;
  private final String name;
  
}
