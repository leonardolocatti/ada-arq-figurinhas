package br.com.llocatti.domain.repositories;

import br.com.llocatti.domain.entities.AlbumCopy;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AlbumCopiesRepository {
  void save(AlbumCopy albumCopy);

  Set<AlbumCopy> findAll();

  Optional<AlbumCopy> findById(UUID id);
}
