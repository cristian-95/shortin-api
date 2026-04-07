package com.cristian.shortin_api.url.core;

import com.cristian.shortin_api.url.data.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class UrlServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UrlRepository repository;

    @Test
    void createUrl() {
    }

    @Test
    void getUrl() {
    }

    @Test
    void deleteUrl() {
    }
}