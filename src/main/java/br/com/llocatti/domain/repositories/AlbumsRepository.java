package br.com.llocatti.domain.repositories;

import br.com.llocatti.domain.entities.Album;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AlbumsRepository {
  void save(Album album);

  Set<Album> findAll();

  Optional<Album> findById(UUID id);
}
