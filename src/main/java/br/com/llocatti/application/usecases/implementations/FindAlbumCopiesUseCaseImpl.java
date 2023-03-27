package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import br.com.llocatti.application.dtos.StickerResponse;
import br.com.llocatti.application.usecases.FindAlbumCopiesUseCase;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FindAlbumCopiesUseCaseImpl implements FindAlbumCopiesUseCase {
  private final Logger logger;
  private final AlbumCopiesRepository albumCopiesRepository;

  public FindAlbumCopiesUseCaseImpl(AlbumCopiesRepository albumCopiesRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public Set<AlbumCopyResponse> execute() {
    this.logger.info("Fetching album copies");

    final Set<AlbumCopy> albumCopies = this.albumCopiesRepository.findAll();

    return albumCopies.stream()
        .map(
            albumCopy -> {
              final Set<StickerResponse> stickersResponse =
                  albumCopy.getStickers().stream()
                      .map(
                          sticker ->
                              new StickerResponse(
                                  sticker.getId(),
                                  sticker.getImage(),
                                  sticker.getDescription(),
                                  sticker.getRarity().name(),
                                  sticker.getPrice().doubleValue()))
                      .collect(Collectors.toUnmodifiableSet());

              return new AlbumCopyResponse(
                  albumCopy.getId(), albumCopy.getAlbum().getName(), stickersResponse);
            })
        .collect(Collectors.toUnmodifiableSet());
  }
}
