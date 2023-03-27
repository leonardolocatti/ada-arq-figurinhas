package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.infrastructure.models.StickerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoStickersRepository extends MongoRepository<StickerDocument, String> {}
