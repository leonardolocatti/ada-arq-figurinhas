package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.CreateAlbumRequest;
import br.com.llocatti.application.dtos.CreateAlbumResponse;
import br.com.llocatti.application.dtos.StickerRequest;
import br.com.llocatti.application.dtos.StickerResponse;
import br.com.llocatti.application.usecases.CreateAlbumUseCase;
import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.entities.Sticker;
import br.com.llocatti.domain.repositories.AlbumsRepository;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateAlbumUseCaseImpl implements CreateAlbumUseCase {
  private final Logger logger;
  private final AlbumsRepository albumsRepository;

  public CreateAlbumUseCaseImpl(AlbumsRepository albumsRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumsRepository = albumsRepository;
  }

  @Override
  public CreateAlbumResponse execute(final CreateAlbumRequest createAlbumRequest) {
    this.logger.info("Creating album");

    final Album.Builder albumBuilder = new Album.Builder(createAlbumRequest.name());

    final int total = createAlbumRequest.stickers().size();
    int count = 1;

    for (StickerRequest stickerRequest : createAlbumRequest.stickers()) {
      this.logger.info("Creating sticker [{}/{}]", count, total);

      albumBuilder.addSticker(
          stickerRequest.image(),
          stickerRequest.description(),
          Sticker.Rarity.getByName(stickerRequest.rarity()),
          BigDecimal.valueOf(stickerRequest.price()));

      count += 1;
    }

    final Album album = albumBuilder.build();

    this.logger.info("Saving album");

    this.albumsRepository.save(album);

    final Set<StickerResponse> createStickersResponse =
        album.getStickers().stream()
            .map(
                sticker ->
                    new StickerResponse(
                        sticker.getId(),
                        sticker.getImage(),
                        sticker.getDescription(),
                        sticker.getRarity().name(),
                        sticker.getPrice().doubleValue()))
            .collect(Collectors.toUnmodifiableSet());

    return new CreateAlbumResponse(album.getId(), album.getName(), createStickersResponse);
  }
}
