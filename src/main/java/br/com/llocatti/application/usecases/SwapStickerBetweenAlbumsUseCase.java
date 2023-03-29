package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.SwapStickerRequest;

public interface SwapStickerBetweenAlbumsUseCase {
  void execute(SwapStickerRequest swapStickerRequest);
}
