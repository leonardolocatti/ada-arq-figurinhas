package br.com.llocatti.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StickerTest {
  private static final String IMAGE = "imageTest";
  private static final String DESCRIPTION = "descriptionTest";
  private static final Sticker.Rarity RARITY = Sticker.Rarity.RATE_ONE;
  private static final BigDecimal PRICE = BigDecimal.TEN;

  @Test
  void testConstructSticker() {
    Sticker sticker = new Sticker(IMAGE, DESCRIPTION, RARITY, PRICE);

    assertAll(
        () -> assertThat(sticker.getId()).isInstanceOf(UUID.class),
        () -> assertThat(sticker.getImage()).isEqualTo(IMAGE),
        () -> assertThat(sticker.getDescription()).isEqualTo(DESCRIPTION),
        () -> assertThat(sticker.getRarity()).isEqualTo(RARITY),
        () -> assertThat(sticker.getPrice()).isEqualTo(PRICE));
  }
}
