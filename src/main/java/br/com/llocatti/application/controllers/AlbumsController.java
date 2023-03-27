package br.com.llocatti.application.controllers;

import br.com.llocatti.application.dtos.CreateAlbumRequest;
import br.com.llocatti.application.dtos.CreateAlbumResponse;
import br.com.llocatti.application.dtos.FindAlbumResponse;
import br.com.llocatti.application.usecases.CreateAlbumUseCase;
import br.com.llocatti.application.usecases.FindAlbumsUseCase;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("albums")
public class AlbumsController {
  private final Logger logger;
  private final CreateAlbumUseCase createAlbumUseCase;
  private final FindAlbumsUseCase findAlbumsUseCase;

  public AlbumsController(
      CreateAlbumUseCase createAlbumUseCase, FindAlbumsUseCase findAlbumsUseCase) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.createAlbumUseCase = createAlbumUseCase;
    this.findAlbumsUseCase = findAlbumsUseCase;
  }

  @PostMapping
  public ResponseEntity<CreateAlbumResponse> createAlbum(
      @RequestBody final CreateAlbumRequest createAlbumRequest) {
    this.logger.info("Received create album request");

    final CreateAlbumResponse createAlbumResponse =
        this.createAlbumUseCase.execute(createAlbumRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(createAlbumResponse);
  }

  @GetMapping
  public ResponseEntity<Set<FindAlbumResponse>> findAlbums() {
    this.logger.info("Received find albums request");

    final Set<FindAlbumResponse> findAlbumsResponse = this.findAlbumsUseCase.execute();

    return ResponseEntity.ok(findAlbumsResponse);
  }
}
