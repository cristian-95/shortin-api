package com.cristian.shortin_api.url.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
}
