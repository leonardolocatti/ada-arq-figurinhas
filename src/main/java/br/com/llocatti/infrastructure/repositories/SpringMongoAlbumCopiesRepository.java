package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.infrastructure.models.AlbumCopyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringMongoAlbumCopiesRepository extends MongoRepository<AlbumCopyDocument, String> {}
