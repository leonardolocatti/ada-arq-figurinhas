package br.com.llocatti.domain.repositories;

import br.com.llocatti.domain.entities.AlbumCopy;

public interface EventsRepository {
    void publishCreatedAlbumEvent(AlbumCopy albumCopy);
    void readCreationStickersErrorEvent(String event);
}
