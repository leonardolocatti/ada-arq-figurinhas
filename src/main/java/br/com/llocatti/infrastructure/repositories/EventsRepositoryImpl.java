package br.com.llocatti.infrastructure.repositories;

import br.com.llocatti.domain.entities.Album;
import br.com.llocatti.domain.entities.AlbumCopy;
import br.com.llocatti.domain.repositories.AlbumCopiesRepository;
import br.com.llocatti.domain.repositories.EventsRepository;
import br.com.llocatti.infrastructure.models.AlbumCopyDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventsRepositoryImpl implements EventsRepository {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AlbumCopiesRepository albumCopiesRepository;

    public EventsRepositoryImpl(KafkaTemplate<String, String> kafkaTemplate, AlbumCopiesRepository albumCopiesRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.albumCopiesRepository = albumCopiesRepository;
    }

    @Override
    public void publishCreatedAlbumEvent(AlbumCopy albumCopy) {
        try {
            this.kafkaTemplate.send("ALBUM-CREATED", new ObjectMapper().writeValueAsString(albumCopy));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @KafkaListener(topics = "STICKERS-CREATION-ERROR", groupId = "sticker-album")
    public void readCreationStickersErrorEvent(String event) {
        try {
            AlbumCopyDocument albumCopyDocument = new ObjectMapper().readValue(event, AlbumCopyDocument.class);

            this.albumCopiesRepository.delete(UUID.fromString(albumCopyDocument.getId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
