package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.CreateAlbumRequest;
import br.com.llocatti.application.dtos.CreateAlbumResponse;

public interface CreateAlbumUseCase {
  CreateAlbumResponse execute(CreateAlbumRequest createAlbumRequest);
}
