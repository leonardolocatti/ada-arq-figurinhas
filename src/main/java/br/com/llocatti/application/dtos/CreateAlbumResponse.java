package br.com.llocatti.application.dtos;

import java.util.Set;
import java.util.UUID;

public record CreateAlbumResponse(UUID id, String name, Set<StickerResponse> stickers) {}
