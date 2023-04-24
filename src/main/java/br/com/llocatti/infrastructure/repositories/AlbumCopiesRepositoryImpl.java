package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.entities.Sticker;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import br.com.llocatti.infrastructure.models.AlbumCopyDocument;
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
public class AlbumCopiesRepositoryImpl implements AlbumCopiesRepository {
  private final SpringMongoAlbumCopiesRepository albumCopiesRepository;

  public AlbumCopiesRepositoryImpl(SpringMongoAlbumCopiesRepository albumCopiesRepository) {
    this.albumCopiesRepository = albumCopiesRepository;
  }

  @Override
  public void save(AlbumCopy albumCopy) {
    final List<StickerDocument> allAlbumStickerDocuments =
        albumCopy.getAlbum().getStickers().stream()
            .map(
                sticker ->
                    new StickerDocument(
                        sticker.getId().toString(),
                        sticker.getImage(),
                        sticker.getDescription(),
                        sticker.getRarity().name(),
                        sticker.getPrice().doubleValue()))
            .toList();
    final AlbumDocument albumDocument =
        new AlbumDocument(
            albumCopy.getAlbum().getId().toString(),
            albumCopy.getAlbum().getName(),
            allAlbumStickerDocuments);

    final List<StickerDocument> stickerDocuments =
        albumCopy.getStickers().stream()
            .map(
                sticker ->
                    new StickerDocument(
                        sticker.getId().toString(),
                        sticker.getImage(),
                        sticker.getDescription(),
                        sticker.getRarity().name(),
                        sticker.getPrice().doubleValue()))
            .toList();

    final AlbumCopyDocument albumCopyDocument =
        new AlbumCopyDocument(albumCopy.getId().toString(), albumDocument, stickerDocuments);

    this.albumCopiesRepository.save(albumCopyDocument);
  }

  @Override
  public Set<AlbumCopy> findAll() {
    final List<AlbumCopyDocument> albumCopiesDocument = this.albumCopiesRepository.findAll();

    return albumCopiesDocument.stream()
        .map(
            albumCopyDocument -> {
              final Album.Builder albumBuilder =
                  new Album.Builder(albumCopyDocument.getAlbum().getName())
                      .withId(UUID.fromString(albumCopyDocument.getAlbum().getId()));

              for (StickerDocument stickerDocument : albumCopyDocument.getAlbum().getStickers()) {
                albumBuilder.addSticker(
                    UUID.fromString(stickerDocument.getId()),
                    stickerDocument.getImage(),
                    stickerDocument.getImage(),
                    Sticker.Rarity.getByName(stickerDocument.getRarity()),
                    BigDecimal.valueOf(stickerDocument.getPrice()));
              }

              final Set<Sticker> stickers =
                  albumCopyDocument.getStickers().stream()
                      .map(
                          stickerDocument ->
                              new Sticker(
                                  UUID.fromString(stickerDocument.getId()),
                                  stickerDocument.getImage(),
                                  stickerDocument.getDescription(),
                                  Sticker.Rarity.getByName(stickerDocument.getRarity()),
                                  BigDecimal.valueOf(stickerDocument.getPrice())))
                      .collect(Collectors.toUnmodifiableSet());

              return new AlbumCopy(
                  UUID.fromString(albumCopyDocument.getId()), albumBuilder.build(), stickers);
            })
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Optional<AlbumCopy> findById(UUID id) {
    final Optional<AlbumCopyDocument> albumCopyDocumentOptional =
        this.albumCopiesRepository.findById(id.toString());

    if (albumCopyDocumentOptional.isEmpty()) {
      return Optional.empty();
    }

    final AlbumCopyDocument albumCopyDocument = albumCopyDocumentOptional.get();

    final Album.Builder albumBuilder =
        new Album.Builder(albumCopyDocument.getAlbum().getName())
            .withId(UUID.fromString(albumCopyDocument.getAlbum().getId()));

    for (StickerDocument stickerDocument : albumCopyDocument.getAlbum().getStickers()) {
      albumBuilder.addSticker(
          UUID.fromString(stickerDocument.getId()),
          stickerDocument.getImage(),
          stickerDocument.getImage(),
          Sticker.Rarity.getByName(stickerDocument.getRarity()),
          BigDecimal.valueOf(stickerDocument.getPrice()));
    }

    final Set<Sticker> stickers =
        albumCopyDocument.getStickers().stream()
            .map(
                stickerDocument ->
                    new Sticker(
                        UUID.fromString(stickerDocument.getId()),
                        stickerDocument.getImage(),
                        stickerDocument.getDescription(),
                        Sticker.Rarity.getByName(stickerDocument.getRarity()),
                        BigDecimal.valueOf(stickerDocument.getPrice())))
            .collect(Collectors.toUnmodifiableSet());

    return Optional.of(
        new AlbumCopy(UUID.fromString(albumCopyDocument.getId()), albumBuilder.build(), stickers));
  }

  @Override
  public void delete(UUID id) {
    this.albumCopiesRepository.deleteById(id.toString());
  }
}
