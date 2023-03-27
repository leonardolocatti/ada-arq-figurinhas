package br.com.llocatti.application.usecases;

import br.com.llocatti.application.dtos.FindAlbumResponse;
import java.util.Set;

public interface FindAlbumsUseCase {
  Set<FindAlbumResponse> execute();
}
