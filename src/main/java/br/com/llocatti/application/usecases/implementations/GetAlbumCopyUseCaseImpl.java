package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import br.com.llocatti.application.dtos.StickerResponse;
import br.com.llocatti.application.usecases.GetAlbumCopyUseCase;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class GetAlbumCopyUseCaseImpl implements GetAlbumCopyUseCase {
  private final Logger logger;
  private final AlbumCopiesRepository albumCopiesRepository;

  public GetAlbumCopyUseCaseImpl(AlbumCopiesRepository albumCopiesRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public AlbumCopyResponse execute(UUID id) {
    this.logger.info("Fetching album copy");

    final Optional<AlbumCopy> albumCopyOptional = this.albumCopiesRepository.findById(id);

    if (albumCopyOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album copy not found");
    }

    final AlbumCopy albumCopy = albumCopyOptional.get();

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
  }
}
