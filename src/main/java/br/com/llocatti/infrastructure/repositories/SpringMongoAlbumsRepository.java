package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.infrastructure.models.AlbumDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringMongoAlbumsRepository extends MongoRepository<AlbumDocument, String> {}
