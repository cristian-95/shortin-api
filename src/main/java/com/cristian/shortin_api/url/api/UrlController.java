package com.cristian.shortin_api.url.api;

import com.cristian.shortin_api.url.core.UrlService;
import com.cristian.shortin_api.url.dto.UrlDTO;
import com.cristian.shortin_api.url.dto.UrlRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "URLs", description = "Operações relacionadas a URLs.")
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten")
    @Operation(summary = "Encurtar URL", description = "gera um código curto para redirecionar para a URL fornecida")
    @ApiResponse(responseCode = "400", description = "URL inválida.")
    public ResponseEntity<UrlDTO> createShortUrl(@RequestBody @Valid UrlRequestDTO dto) throws MalformedURLException {
        UrlDTO shortUrl = urlService.createUrl(dto.url());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{code}")
    @Operation(summary = "Redirecionar", description = "Redireciona para uma URL que foi encurtada.")
    @ApiResponse(responseCode = "401", description = "URL não encontrada.")
    public ResponseEntity<Object> getOriginalUrl(@PathVariable(name = "code") String code) {
        UrlDTO url = urlService.getUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.longUrl())).build();
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Remover URL", description = "Remove uma URL encurtada do banco de dados")
    @ApiResponse(responseCode = "401", description = "URL não encontrada.")
    public ResponseEntity<Void> deleteUrl(@PathVariable(name = "code") String    code) {
        urlService.deleteUrl(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
