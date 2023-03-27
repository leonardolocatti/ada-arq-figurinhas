package br.com.llocatti.infrastructure.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("album_copies")
public class AlbumCopyDocument {
  @Id private String id;
  @DBRef private AlbumDocument album;
  @DBRef private List<StickerDocument> stickers;

  public AlbumCopyDocument(String id, AlbumDocument album, List<StickerDocument> stickers) {
    this.id = id;
    this.album = album;
    this.stickers = stickers;
  }

  public String getId() {
    return this.id;
  }

  public AlbumDocument getAlbum() {
    return this.album;
  }

  public List<StickerDocument> getStickers() {
    return this.stickers;
  }
}
