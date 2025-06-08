package com.cristian.shortin_api.web.dto;

import java.time.LocalDateTime;

public record UrlUpdateDTO(
        Long id,
        String shortUrl,
        String longUrl,
        LocalDateTime expireAt
) {
}
