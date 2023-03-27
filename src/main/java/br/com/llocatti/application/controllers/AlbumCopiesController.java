package br.com.llocatti.application.controllers;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import br.com.llocatti.application.dtos.CreateAlbumCopyRequest;
import br.com.llocatti.application.usecases.AddAlbumToStickerUseCase;
import br.com.llocatti.application.usecases.CreateAlbumCopyUseCase;
import br.com.llocatti.application.usecases.FindAlbumCopiesUseCase;
import br.com.llocatti.application.usecases.GetAlbumCopyUseCase;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("album_copies")
public class AlbumCopiesController {
  private final Logger logger;
  private final CreateAlbumCopyUseCase createAlbumCopyUseCase;
  private final FindAlbumCopiesUseCase findAlbumCopiesUseCase;
  private final GetAlbumCopyUseCase getAlbumCopyUseCase;
  private final AddAlbumToStickerUseCase addAlbumToStickerUseCase;

  public AlbumCopiesController(
      CreateAlbumCopyUseCase createAlbumCopyUseCase,
      FindAlbumCopiesUseCase findAlbumCopiesUseCase,
      GetAlbumCopyUseCase getAlbumCopyUseCase,
      AddAlbumToStickerUseCase addAlbumToStickerUseCase) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.createAlbumCopyUseCase = createAlbumCopyUseCase;
    this.findAlbumCopiesUseCase = findAlbumCopiesUseCase;
    this.getAlbumCopyUseCase = getAlbumCopyUseCase;
    this.addAlbumToStickerUseCase = addAlbumToStickerUseCase;
  }

  @PostMapping
  public ResponseEntity<AlbumCopyResponse> createAlbumCopy(
      @RequestBody final CreateAlbumCopyRequest createAlbumCopyRequest) {
    this.logger.info("Received create album copy request");

    final AlbumCopyResponse albumCopyResponse =
        this.createAlbumCopyUseCase.execute(createAlbumCopyRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(albumCopyResponse);
  }

  @GetMapping
  public ResponseEntity<Set<AlbumCopyResponse>> findAlbumCopies() {
    this.logger.info("Received find album copies request");

    final Set<AlbumCopyResponse> albumCopiesResponse = this.findAlbumCopiesUseCase.execute();

    return ResponseEntity.ok(albumCopiesResponse);
  }

  @GetMapping("{id}")
  public ResponseEntity<AlbumCopyResponse> getAlbumCopy(@PathVariable final UUID id) {
    this.logger.info("Received get album copy request");

    final AlbumCopyResponse albumCopyResponse = this.getAlbumCopyUseCase.execute(id);

    return ResponseEntity.ok(albumCopyResponse);
  }

  @PostMapping("{id}/stickers/{stickerId}")
  public ResponseEntity<AlbumCopyResponse> addStickerToAlbumCopy(
      @PathVariable final UUID id, @PathVariable final UUID stickerId) {
    this.logger.info("Received add sticker to album request");

    this.addAlbumToStickerUseCase.execute(id, stickerId);
    final AlbumCopyResponse albumCopyResponse = this.getAlbumCopyUseCase.execute(id);

    return ResponseEntity.ok(albumCopyResponse);
  }
}
