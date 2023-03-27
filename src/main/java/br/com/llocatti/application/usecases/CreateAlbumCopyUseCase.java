package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import br.com.llocatti.application.dtos.CreateAlbumCopyRequest;

public interface CreateAlbumCopyUseCase {
  AlbumCopyResponse execute(CreateAlbumCopyRequest createAlbumCopyRequest);
}
