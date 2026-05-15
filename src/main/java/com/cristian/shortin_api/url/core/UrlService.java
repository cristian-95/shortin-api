package com.cristian.shortin_api.url.core;

import com.cristian.shortin_api.infra.exception.UrlNotFoundException;
import com.cristian.shortin_api.url.data.Url;
import com.cristian.shortin_api.url.data.UrlRepository;
import com.cristian.shortin_api.url.dto.UrlDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import java.net.MalformedURLException;

@Service
@ConfigurationProperties(prefix = "api")
@RequiredArgsConstructor
public class UrlService {

    @Value("${api.base-url}")
    String baseUrl;
    private final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlEncoder urlEncoder;
    private final UrlRepository urlRepository;

    @Transactional
    public UrlDTO createUrl(String longUrl) throws MalformedURLException {
        logger.info("Creating short url from: {}", longUrl);

        String urlString = normalize(longUrl);
        String code = urlEncoder.getShortCode(urlString);
        String shortUrl = baseUrl + "/" + code;

        Url url = new Url(code, urlString);
        urlRepository.save(url);
        return new UrlDTO(url.getLongUrl(), shortUrl);
    }

    private String normalize(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) throw new InvalidUrlException("URL is required");
        if (!longUrl.startsWith("http")) longUrl = "https://" + longUrl;
        if (longUrl.endsWith("/")) longUrl = longUrl.substring(0, longUrl.length() - 1);
        return longUrl;
    }

    public UrlDTO getUrl(String code) {
        logger.info("Retrieving long url from: {}", code);
        Url url = findUrl(code);
        return new UrlDTO(url.getLongUrl(), baseUrl + "/" + url.getCode());
    }

    @Transactional
    public void deleteUrl(String code) {
        logger.info("Deleting url: {}", code);
        Url url = findUrl(code);
        this.urlRepository.delete(url);
    }


    private Url findUrl(String code) {
        return urlRepository
                .findById(code)
                .orElseThrow(() -> new UrlNotFoundException("Url not found."));
    }
}
