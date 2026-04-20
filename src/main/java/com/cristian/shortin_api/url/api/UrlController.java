package com.cristian.shortin_api.url.api;

import com.cristian.shortin_api.url.core.UrlService;
import com.cristian.shortin_api.url.dto.UrlDTO;
import com.cristian.shortin_api.url.dto.UrlRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlDTO> createShortUrl(@RequestBody UrlRequestDTO dto) {
        UrlDTO shortUrl = urlService.createUrl(dto.url());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> getOriginalUrl(@PathVariable(name = "code") String code) {
        UrlDTO url = urlService.getUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.longUrl())).build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteUrl(@PathVariable(name = "code") String code) {
        urlService.deleteUrl(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
