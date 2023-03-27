package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import java.util.UUID;

public interface GetAlbumCopyUseCase {
  AlbumCopyResponse execute(UUID id);
}
