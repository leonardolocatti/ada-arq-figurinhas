package br.com.llocatti.infrastructure.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("albums")
public class AlbumDocument {
  @Id private String id;
  private String name;
  @DBRef private List<StickerDocument> stickers;

  public AlbumDocument(String id, String name, List<StickerDocument> stickers) {
    this.id = id;
    this.name = name;
    this.stickers = stickers;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<StickerDocument> getStickers() {
    return this.stickers;
  }
}
