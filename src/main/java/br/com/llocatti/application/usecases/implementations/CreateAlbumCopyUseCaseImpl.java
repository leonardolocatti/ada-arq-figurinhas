package br.com.llocatti.application.usecases.implementations;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import br.com.llocatti.application.dtos.CreateAlbumCopyRequest;
import br.com.llocatti.application.usecases.CreateAlbumCopyUseCase;
import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import br.com.llocatti.domain.repositories.AlbumsRepository;
import java.util.Collections;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CreateAlbumCopyUseCaseImpl implements CreateAlbumCopyUseCase {
  private final Logger logger;
  private final AlbumsRepository albumsRepository;
  private final AlbumCopiesRepository albumCopiesRepository;

  public CreateAlbumCopyUseCaseImpl(
      AlbumsRepository albumsRepository, AlbumCopiesRepository albumCopiesRepository) {
    this.logger = LoggerFactory.getLogger(this.getClass());
    this.albumsRepository = albumsRepository;
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public AlbumCopyResponse execute(CreateAlbumCopyRequest createAlbumCopyRequest) {
    this.logger.info("Creating album copy");

    final Optional<Album> optionalAlbum =
        this.albumsRepository.findById(createAlbumCopyRequest.album());

    if (optionalAlbum.isEmpty()) {
      this.logger.warn("Album not exists");

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album not exists");
    }

    final Album album = optionalAlbum.get();

    final AlbumCopy albumCopy = new AlbumCopy(album);

    this.logger.info("Saving album copy");

    this.albumCopiesRepository.save(albumCopy);

    return new AlbumCopyResponse(
        albumCopy.getId(), albumCopy.getAlbum().getName(), Collections.emptySet());
  }
}
