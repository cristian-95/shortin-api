package com.cristian.shortin_api.url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UrlRequestDTO(
        @NotBlank(message = "URL is required")
        @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", message = "Invalid URL, please check your spelling and try again.")
        String url) {
}

