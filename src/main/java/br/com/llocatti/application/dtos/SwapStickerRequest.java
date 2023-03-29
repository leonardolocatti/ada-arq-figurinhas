package br.com.llocatti.application.dtos;

import java.util.UUID;

public record SwapStickerRequest(UUID origin, UUID destiny, UUID sticker) {}
