package br.com.llocatti.application.dtos;

import java.util.UUID;

public record StickerResponse(
    UUID id, String image, String description, String rarity, Double price) {}
