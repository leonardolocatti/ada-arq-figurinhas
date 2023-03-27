package br.com.llocatti.application.usecases;

import java.util.UUID;

public interface AddAlbumToStickerUseCase {
  void execute(UUID id, UUID stickerId);
}
