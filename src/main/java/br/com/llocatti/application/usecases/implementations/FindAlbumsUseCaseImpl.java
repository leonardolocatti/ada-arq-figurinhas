package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.FindAlbumResponse;
import br.com.llocatti.application.dtos.StickerResponse;
import br.com.llocatti.application.usecases.FindAlbumsUseCase;
import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.repositories.AlbumsRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FindAlbumsUseCaseImpl implements FindAlbumsUseCase {
  private final Logger logger;
  private final AlbumsRepository albumsRepository;

  public FindAlbumsUseCaseImpl(AlbumsRepository albumsRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumsRepository = albumsRepository;
  }

  @Override
  public Set<FindAlbumResponse> execute() {
    this.logger.info("Fetching albums");

    final Set<Album> albums = this.albumsRepository.findAll();

    return albums.stream()
        .map(
            album -> {
              final Set<StickerResponse> stickersResponse =
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

              return new FindAlbumResponse(album.getId(), album.getName(), stickersResponse);
            })
        .collect(Collectors.toUnmodifiableSet());
  }
}
