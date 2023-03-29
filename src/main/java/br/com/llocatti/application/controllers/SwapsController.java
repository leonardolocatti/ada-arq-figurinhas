package br.com.llocatti.application.controllers;

import br.com.llocatti.application.dtos.SwapStickerRequest;
import br.com.llocatti.application.usecases.SwapStickerBetweenAlbumsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("swaps")
public class SwapsController {
  private final Logger logger;
  private final SwapStickerBetweenAlbumsUseCase swapStickerBetweenAlbumsUseCase;

  public SwapsController(SwapStickerBetweenAlbumsUseCase swapStickerBetweenAlbumsUseCase) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.swapStickerBetweenAlbumsUseCase = swapStickerBetweenAlbumsUseCase;
  }

  @PostMapping
  public ResponseEntity<Void> swapStickerBetweenAlbumCopies(
      @RequestBody final SwapStickerRequest swapStickerRequest) {
    this.logger.info("Received swap sticker between album copies request");

    this.swapStickerBetweenAlbumsUseCase.execute(swapStickerRequest);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
