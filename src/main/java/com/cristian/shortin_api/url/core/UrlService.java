package com.cristian.shortin_api.url.core;

import com.cristian.shortin_api.url.data.Url;
import com.cristian.shortin_api.url.data.UrlRepository;
import com.cristian.shortin_api.url.dto.UrlDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "api")
public class UrlService {

    // TODO: Implement exception handler and support to custom exceptions

    @Value("${api.base-url}")
    String baseUrl;
    private final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlEncoder urlEncoder;
    @Autowired
    private UrlRepository urlRepository;

    @PostConstruct
    public void init() {
        logger.info("base-url: {}", baseUrl);
    }

    @Transactional
    public UrlDTO createUrl(String longUrl) {
        logger.info("Creating short url from: {}", longUrl);

        String code = urlEncoder.getShortCode(longUrl);
        String shortUrl = baseUrl + "/" + code;
        Url url = new Url(code, longUrl);
        urlRepository.save(url);
        return new UrlDTO(url.getLongUrl(), shortUrl);
    }

    public UrlDTO getUrl(String shortUrl) {
        logger.info("Retrieving long url from: {}", shortUrl);
        Url url = findUrl(shortUrl);
        return new UrlDTO(url.getLongUrl(), baseUrl + "/" + url.getCode());
    }

    @Transactional
    public void deleteUrl(String shortUrl) {
        logger.info("Deleting url: {}", shortUrl);
        Url url = findUrl(shortUrl);
        this.urlRepository.delete(url);
    }

    private Url findUrl(String code) {
        if (code.length() > 6 && code.contains(baseUrl)) {
            code = code.substring(baseUrl.length() + 1);
        }
        String finalCode = code;
        return urlRepository.findById(code).orElseThrow(() -> new RuntimeException("Url "+ finalCode +" not found"));
    }
}
