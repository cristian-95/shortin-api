package com.cristian.shortin_api.url.core;

import com.cristian.shortin_api.url.data.Url;
import com.cristian.shortin_api.url.data.UrlRepository;
import com.cristian.shortin_api.url.dto.UrlDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "api")
public class UrlService {

    // TODO: Implement exception handler and support to custom exceptions

    String baseUrl;
    private final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlEncoder urlEncoder;
    @Autowired
    private UrlRepository urlRepository;

    @Transactional
    public UrlDTO createUrl(String longUrl) {
        logger.info("Creating short url from: {}", longUrl);
        String URL_REGEX = "(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
        if (!longUrl.matches(URL_REGEX)) {
            throw new RuntimeException("Invalid url");
        }
        String code = urlEncoder.getShortCode(longUrl);
        String shortUrl = baseUrl + "/" + code;
        Url url = new Url(code, longUrl, shortUrl);
        urlRepository.save(url);
        return null;
    }

    public UrlDTO getUrl(String shortUrl) {
        logger.info("Retrieving long url from: {}", shortUrl);
        Url url = findUrl(shortUrl);
        return new UrlDTO(url.getLongUrl(), url.getShortUrl());
    }

    @Transactional
    public void deleteUrl(String shortUrl) {
        logger.info("Deleting url: {}", shortUrl);
        Url url = findUrl(shortUrl);
        this.urlRepository.delete(url);
    }

    private Url findUrl(String shortUrl) {
        String code = shortUrl.substring(baseUrl.length() + 1);
        return urlRepository.findById(code).orElseThrow(() -> new RuntimeException("Url not found"));
    }
}
