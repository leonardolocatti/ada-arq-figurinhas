package br.com.llocatti.infrastructure.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("stickers")
public class StickerDocument {
  private String id;
  private String image;
  private String description;
  private String rarity;
  private double price;

  public StickerDocument(String id, String image, String description, String rarity, double price) {
    this.id = id;
    this.image = image;
    this.description = description;
    this.rarity = rarity;
    this.price = price;
  }

  public String getId() {
    return this.id;
  }

  public String getImage() {
    return this.image;
  }

  public String getDescription() {
    return this.description;
  }

  public String getRarity() {
    return this.rarity;
  }

  public double getPrice() {
    return this.price;
  }
}
