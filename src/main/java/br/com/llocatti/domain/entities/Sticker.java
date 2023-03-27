package br.com.llocatti.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public final class Sticker {
  private final UUID id;
  private final String image;
  private final String description;
  private final Rarity rarity;
  private final BigDecimal price;

  public Sticker(UUID id, String image, String description, Rarity rarity, BigDecimal price) {
    this.id = id;
    this.image = image;
    this.description = description;
    this.rarity = rarity;
    this.price = price;
  }

  public Sticker(String image, String description, Rarity rarity, BigDecimal price) {
    this(UUID.randomUUID(), image, description, rarity, price);
  }

  public UUID getId() {
    return this.id;
  }

  public String getImage() {
    return this.image;
  }

  public String getDescription() {
    return this.description;
  }

  public Rarity getRarity() {
    return this.rarity;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public enum Rarity {
    RATE_ONE(1),
    RATE_TWO(3),
    RATE_THREE(6),
    RATE_FOUR(10);

    private final int amountPerAlbum;

    Rarity(int amountPerAlbum) {
      this.amountPerAlbum = amountPerAlbum;
    }

    public static Rarity getByName(String name) {
      for (Rarity rarity : Rarity.values()) {
        if (name.equals(rarity.name())) {
          return rarity;
        }
      }

      throw new IllegalArgumentException(
          String.format("The value '%s' is not a valid rarity", name));
    }
  }
}
