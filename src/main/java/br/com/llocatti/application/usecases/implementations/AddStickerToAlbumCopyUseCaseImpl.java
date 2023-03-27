package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.*;
import br.com.llocatti.application.usecases.AddAlbumToStickerUseCase;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.entities.Sticker;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AddStickerToAlbumCopyUseCaseImpl implements AddAlbumToStickerUseCase {
  private final Logger logger;
  private final AlbumCopiesRepository albumCopiesRepository;

  public AddStickerToAlbumCopyUseCaseImpl(AlbumCopiesRepository albumCopiesRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public void execute(UUID id, UUID stickerId) {
    Optional<AlbumCopy> albumCopyOptional = this.albumCopiesRepository.findById(id);

    if (albumCopyOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album copy doesn't exists");
    }

    AlbumCopy albumCopy = albumCopyOptional.get();

    Optional<Sticker> stickerOptional =
        albumCopy.getAlbum().getStickers().stream()
            .filter(sticker -> sticker.getId().equals(stickerId))
            .findFirst();

    if (stickerOptional.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Sticker doesn't exists or not belongs to the album");
    }

    albumCopy.addSticker(stickerOptional.get());

    this.albumCopiesRepository.save(albumCopy);
  }
}
