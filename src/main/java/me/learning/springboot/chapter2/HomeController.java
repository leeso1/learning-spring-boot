package me.learning.springboot.chapter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
public class HomeController {

  private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

  private static final String BASE_PATH = "/images/";
  private static final String FILENAME = "{filename:.+}";

  private final ImageService imageService;

  public HomeController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping(value = BASE_PATH + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  public Mono<ResponseEntity<?>> oneRawImage(
          @PathVariable String fileName) {
    return imageService.getImageByName(fileName)
           .map(resource -> {
             try {
               return ResponseEntity.ok()
                       .contentLength(resource.contentLength())
                       .body(new InputStreamResource(resource.getInputStream()));
             }
             catch (IOException e) {
               return ResponseEntity.badRequest()
                       .body("Couldn't find " + fileName + " => " + e.getMessage());
             }
           });
  }

  /**
   * 파일 생성 요청
   * @param files - 생성할 files
   * @return 파일 생성 후 / redirection 됨
   */
  @PostMapping(value = BASE_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Mono<String> createFile(
          @RequestPart(name = "file") Flux<FilePart> files
          ) {
    LOG.info("IF-001");
    return imageService.createImage(files)
            .then(Mono.just("redirect:/"));
  }

  /**
   * 전달된 파일명 삭제
   *
   * @param filename - 삭제할 파일명
   * @return /로 redirection 됨.
   */
  @DeleteMapping(value = BASE_PATH + FILENAME)
  public Mono<String> deleteFile(
          @PathVariable String filename
  ) {
    return imageService.deleteImage(filename)
            .then(Mono.just("redirect:/"));
  }

  @GetMapping("/")
  public Mono<String> index(Model model) {
    LOG.info("IF-000");
    model.addAttribute("images", imageService.findAllImages());
    return Mono.just("index");
  }
}
