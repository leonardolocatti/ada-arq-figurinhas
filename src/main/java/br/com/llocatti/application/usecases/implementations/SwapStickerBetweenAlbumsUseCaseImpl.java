package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.SwapStickerRequest;
import br.com.llocatti.application.usecases.SwapStickerBetweenAlbumsUseCase;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.entities.Sticker;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import java.util.Iterator;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SwapStickerBetweenAlbumsUseCaseImpl implements SwapStickerBetweenAlbumsUseCase {
  private final Logger logger;
  private final AlbumCopiesRepository albumCopiesRepository;

  public SwapStickerBetweenAlbumsUseCaseImpl(AlbumCopiesRepository albumCopiesRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public void execute(final SwapStickerRequest swapStickerRequest) {
    final Optional<AlbumCopy> albumCopyOriginOptional =
        this.albumCopiesRepository.findById(swapStickerRequest.origin());

    if (albumCopyOriginOptional.isEmpty()) {
      this.logger.warn("Album copy origin doesn't exists");

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album copy origin doesn't exists");
    }

    final AlbumCopy albumCopyOrigin = albumCopyOriginOptional.get();

    final Optional<AlbumCopy> albumCopyDestinyOptional =
        this.albumCopiesRepository.findById(swapStickerRequest.destiny());

    if (albumCopyDestinyOptional.isEmpty()) {
      this.logger.warn("Album copy destiny doesn't exists");

      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Album copy destiny doesn't exists");
    }

    final AlbumCopy albumCopyDestiny = albumCopyDestinyOptional.get();

    final Iterator<Sticker> stickersAlbumOriginIterator = albumCopyOrigin.getStickers().iterator();

    Sticker swappingSticker = null;

    while (stickersAlbumOriginIterator.hasNext()) {
      Sticker actualSticker = stickersAlbumOriginIterator.next();

      if (actualSticker.getId().equals(swapStickerRequest.sticker())) {
        swappingSticker = actualSticker;
        break;
      }
    }

    if (swappingSticker == null) {
      this.logger.warn("Sticker not found in origin album copy");

      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Sticker not found in origin album copy");
    }

    albumCopyOrigin.removeSticker(swappingSticker);
    albumCopyDestiny.addSticker(swappingSticker);

    this.albumCopiesRepository.save(albumCopyOrigin);
    this.albumCopiesRepository.save(albumCopyDestiny);
  }
}
