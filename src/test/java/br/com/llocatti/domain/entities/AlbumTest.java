package br.com.llocatti.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AlbumTest {
  private static final String ALBUM_NAME = "albumNameTest";
  private static final String STICKER_ONE_IMAGE = "stickerOneImageTest";
  private static final String STICKER_ONE_DESCRIPTION = "stickerOneDescriptionTest";
  private static final Sticker.Rarity STICKER_ONE_RARITY = Sticker.Rarity.RATE_ONE;
  private static final BigDecimal STICKER_ONE_PRICE = BigDecimal.ONE;
  private static final String STICKER_TWO_IMAGE = "stickerTwoImageTest";
  private static final String STICKER_TWO_DESCRIPTION = "stickerTwoDescriptionTest";
  private static final Sticker.Rarity STICKER_TWO_RARITY = Sticker.Rarity.RATE_TWO;
  private static final BigDecimal STICKER_TWO_PRICE = BigDecimal.TEN;

  @Test
  void testCreateAlbum() {
    Album album =
        new Album.Builder(ALBUM_NAME)
            .addSticker(
                STICKER_ONE_IMAGE, STICKER_ONE_DESCRIPTION, STICKER_ONE_RARITY, STICKER_ONE_PRICE)
            .addSticker(
                STICKER_TWO_IMAGE, STICKER_TWO_DESCRIPTION, STICKER_TWO_RARITY, STICKER_TWO_PRICE)
            .build();

    assertAll(
        () -> assertThat(album.getId()).isInstanceOf(UUID.class),
        () -> assertThat(album.getName()).isEqualTo(ALBUM_NAME),
        () ->
            assertThat(album.getStickers())
                .satisfiesExactlyInAnyOrder(
                    stickerOne -> {
                      assertThat(stickerOne).isNotNull();
                      assertAll(
                          () -> assertThat(stickerOne.getImage()).isEqualTo(STICKER_ONE_IMAGE),
                          () ->
                              assertThat(stickerOne.getDescription())
                                  .isEqualTo(STICKER_ONE_DESCRIPTION),
                          () -> assertThat(stickerOne.getRarity()).isEqualTo(STICKER_ONE_RARITY),
                          () -> assertThat(stickerOne.getPrice()).isEqualTo(STICKER_ONE_PRICE));
                    },
                    stickerTwo -> {
                      assertThat(stickerTwo).isNotNull();
                      assertAll(
                          () -> assertThat(stickerTwo.getImage()).isEqualTo(STICKER_TWO_IMAGE),
                          () ->
                              assertThat(stickerTwo.getDescription())
                                  .isEqualTo(STICKER_TWO_DESCRIPTION),
                          () -> assertThat(stickerTwo.getRarity()).isEqualTo(STICKER_TWO_RARITY),
                          () -> assertThat(stickerTwo.getPrice()).isEqualTo(STICKER_TWO_PRICE));
                    }));
  }
}
