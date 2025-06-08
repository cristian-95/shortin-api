package com.cristian.shortin_api.domain.repository;

import com.cristian.shortin_api.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository  extends JpaRepository<Url, Long> {

    Url findByLongUrl(String longUrl);

    Url findByShortUrl(String shortUrl);

    Boolean existUrlByShortUrl(String shortUrl);
}
