package br.com.llocatti.application.dtos;

import java.util.Set;

public record CreateAlbumRequest(String name, Set<StickerRequest> stickers) {}
