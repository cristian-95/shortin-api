package com.cristian.shortin_api.url.api;

import com.cristian.shortin_api.url.core.UrlService;
import com.cristian.shortin_api.url.dto.UrlDTO;
import com.cristian.shortin_api.url.dto.UrlRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlDTO> createShortUrl(@RequestBody UrlRequestDTO dto) {
        UrlDTO shortUrl = urlService.createUrl(dto.url());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{code}")
    public ResponseEntity<UrlDTO> getOriginalUrl(@PathVariable(name = "code") String code) {
        UrlDTO url = urlService.getUrl(code);
        return ResponseEntity.ok(url);
    }
}
