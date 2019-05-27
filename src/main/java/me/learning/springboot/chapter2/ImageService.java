package me.learning.springboot.chapter2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ImageService {

  private static Logger LOG = LoggerFactory.getLogger(ImageService.class);

  private static String UPLOAD_ROOT = "/Users/leesoch/IdeaProjects/images";

  private final ResourceLoader resourceLoader;

  public ImageService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
    LOG.info("Created ImageService");
  }

  /**
   * Finding all images
   * @return image list
   */
  public Flux<Image> findAllImages() {
    try {
      return Flux.fromIterable(
          Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
          .map(path -> new Image(
              String.valueOf(path.hashCode()),
              path.getFileName().toString()));
    } 
    catch (IOException e) {
      return Flux.empty();
    }
  }

  /**
   * Finding a image file
   * @param fileName - image file name
   * @return image file
   */
  public Mono<Resource> getImageByName(String fileName) {
    return Mono.fromSupplier(
        () -> resourceLoader.getResource(UPLOAD_ROOT + "/" + fileName));
  }

  /**
   * Creating image
   * @param files - Part for uploading files
   * @return Mono<Void>
   */
  public Mono<Void> createImage(Flux<FilePart> files) {
    return files.flatMap(file -> file.transferTo(
            Paths.get(UPLOAD_ROOT, file.filename()).toFile())).then();
  }

  /**
   * Deleting image
   * @param fileName imnage name
   * @return Mono<Void>
   */
  public Mono<Void> deleteImage(String fileName) {
    return Mono.fromRunnable(() -> {
      try {
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
      }
      catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    });
  }

}
