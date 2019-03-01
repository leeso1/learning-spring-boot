package me.learning.springboot.chapter1;

import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Chapter {

    @Id
    private String id;
    private String name;

    public Chapter(String name) {
        this.name = name;
    }

}
