package br.com.llocatti.domain.entities;

import java.math.BigDecimal;
import java.util.*;

public class Album {
  private final UUID id;
  private final String name;
  private final Set<Sticker> stickers;

  private Album(Builder builder) {
    this.id = Optional.ofNullable(builder.id).orElse(UUID.randomUUID());
    this.name = builder.name;
    this.stickers = Collections.unmodifiableSet(builder.stickers);
  }

  public UUID getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Set<Sticker> getStickers() {
    return this.stickers;
  }

  public static final class Builder {
    private final String name;
    private final Set<Sticker> stickers;
    private UUID id;

    public Builder(String name) {
      this.name = name;
      this.stickers = new HashSet<>();
    }

    public Builder addSticker(
        UUID id, String image, String description, Sticker.Rarity rarity, BigDecimal price) {
      Sticker sticker = new Sticker(id, image, description, rarity, price);

      this.stickers.add(sticker);

      return this;
    }

    public Builder addSticker(
        String image, String description, Sticker.Rarity rarity, BigDecimal price) {
      Sticker sticker = new Sticker(image, description, rarity, price);

      this.stickers.add(sticker);

      return this;
    }

    public Builder withId(UUID id) {
      this.id = id;

      return this;
    }

    public Album build() {
      return new Album(this);
    }
  }
}
