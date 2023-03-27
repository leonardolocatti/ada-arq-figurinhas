package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.entities.Sticker;
import br.com.llocatti.domain.repositories.AlbumsRepository;
import br.com.llocatti.infrastructure.models.AlbumDocument;
import br.com.llocatti.infrastructure.models.StickerDocument;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AlbumsRepositoryImpl implements AlbumsRepository {
  private final SpringMongoStickersRepository mongoStickersRepository;
  private final SpringMongoAlbumsRepository mongoAlbumsRepository;

  public AlbumsRepositoryImpl(
      SpringMongoStickersRepository mongoStickersRepository,
      SpringMongoAlbumsRepository mongoAlbumsRepository) {
    this.mongoStickersRepository = mongoStickersRepository;
    this.mongoAlbumsRepository = mongoAlbumsRepository;
  }

  @Override
  public void save(Album album) {
    List<StickerDocument> stickerDocuments =
        album.getStickers().stream()
            .map(
                sticker ->
                    new StickerDocument(
                        sticker.getId().toString(),
                        sticker.getImage(),
                        sticker.getDescription(),
                        sticker.getRarity().name(),
                        sticker.getPrice().doubleValue()))
            .toList();

    this.mongoStickersRepository.saveAll(stickerDocuments);
    this.mongoAlbumsRepository.save(
        new AlbumDocument(album.getId().toString(), album.getName(), stickerDocuments));
  }

  @Override
  public Set<Album> findAll() {
    List<AlbumDocument> albumDocuments = this.mongoAlbumsRepository.findAll();

    return albumDocuments.stream()
        .map(
            albumDocument -> {
              final Album.Builder albumBuilder =
                  new Album.Builder(albumDocument.getName())
                      .withId(UUID.fromString(albumDocument.getId()));

              for (StickerDocument stickerDocument : albumDocument.getStickers()) {
                albumBuilder.addSticker(
                    UUID.fromString(stickerDocument.getId()),
                    stickerDocument.getImage(),
                    stickerDocument.getImage(),
                    Sticker.Rarity.getByName(stickerDocument.getRarity()),
                    BigDecimal.valueOf(stickerDocument.getPrice()));
              }

              return albumBuilder.build();
            })
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Optional<Album> findById(UUID id) {
    Optional<AlbumDocument> albumDocumentOptional =
        this.mongoAlbumsRepository.findById(id.toString());

    if (albumDocumentOptional.isEmpty()) {
      return Optional.empty();
    }

    final AlbumDocument albumDocument = albumDocumentOptional.get();

    final Album.Builder albumBuilder =
        new Album.Builder(albumDocument.getName()).withId(UUID.fromString(albumDocument.getId()));

    for (StickerDocument stickerDocument : albumDocument.getStickers()) {
      albumBuilder.addSticker(
          UUID.fromString(stickerDocument.getId()),
          stickerDocument.getImage(),
          stickerDocument.getImage(),
          Sticker.Rarity.getByName(stickerDocument.getRarity()),
          BigDecimal.valueOf(stickerDocument.getPrice()));
    }

    return Optional.of(albumBuilder.build());
  }
}
