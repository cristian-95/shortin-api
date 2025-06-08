package com.cristian.shortin_api.domain.service;

import com.cristian.shortin_api.domain.model.Url;
import com.cristian.shortin_api.domain.repository.UrlRepository;
import com.cristian.shortin_api.web.dto.UrlUpdateDTO;
import com.cristian.shortin_api.web.mapper.UrlMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.cristian.shortin_api.domain.model.UrlConstants.URL_REGEX;

@Service
public class UrlService {

    Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlMapper urlMapper;

    @Value("${generator.length;5}")
    private int LENGTH;

    @Transactional
    public Url generateShortUrl(String longUrl, LocalDateTime expireAt) {
        if (!longUrl.matches(URL_REGEX)) {
            return null;
        }
        if (this.get(longUrl) != null) {
            return this.get(longUrl);
        }
        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        newUrl.setShortUrl(this.generateShortUrlString());
        newUrl.setExpireAt(expireAt);
        return this.urlRepository.save(newUrl);
    }

    public Url get(String shortUrl) {
        logger.info("Retrieving long url from {}", shortUrl);
        return this.urlRepository.findByShortUrl(shortUrl);
    }

    @Transactional
    public Url updateUrl(UrlUpdateDTO dto) {
        logger.info("Updating url");
        Url url = this.urlRepository.findById(dto.id()).orElseThrow(() -> new RuntimeException("Url not found"));
        urlMapper.updateFromDTO(dto, url);
        return this.urlRepository.save(url);
    }


    @Transactional
    public void deleteUrl(String shortUrl) {
        logger.info("Deleting url");
        Url url = this.urlRepository.findByShortUrl(shortUrl);
        this.urlRepository.delete(url);
    }

    private String generateShortUrlString() {
        logger.info("Generating short url String");
        return RandomStringUtils.randomAlphanumeric(LENGTH);
    }
}
