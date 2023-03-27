package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.AlbumCopyResponse;
import java.util.Set;

public interface FindAlbumCopiesUseCase {
  Set<AlbumCopyResponse> execute();
}
