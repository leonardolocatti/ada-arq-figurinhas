package br.com.llocatti.application.dtos;

import java.util.Set;
import java.util.UUID;

public record AlbumCopyResponse(UUID id, String albumName, Set<StickerResponse> stickers) {}
