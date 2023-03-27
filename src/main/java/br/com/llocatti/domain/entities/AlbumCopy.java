package br.com.llocatti.domain.entities;

import java.util.*;

public class AlbumCopy {
  private final UUID id;
  private final Album album;
  private final Set<Sticker> stickers;

  public AlbumCopy(UUID id, Album album, Set<Sticker> stickers) {
    this.id = id;
    this.album = album;
    this.stickers = new HashSet<>(stickers);
  }

  public AlbumCopy(Album album) {
    this(UUID.randomUUID(), album, new HashSet<>());
  }

  public void addSticker(Sticker sticker) {
    this.stickers.add(sticker);
  }

  public UUID getId() {
    return this.id;
  }

  public Album getAlbum() {
    return this.album;
  }

  public Set<Sticker> getStickers() {
    return this.stickers;
  }
}
