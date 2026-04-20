package com.cristian.shortin_api.url.core;

import com.cristian.shortin_api.infra.exception.UrlNotFoundException;
import com.cristian.shortin_api.url.data.Url;
import com.cristian.shortin_api.url.data.UrlRepository;
import com.cristian.shortin_api.url.dto.UrlDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.InvalidUrlException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    @Autowired
    private UrlService service;
    @MockitoBean
    private UrlEncoder urlEncoder;
    @MockitoBean
    private UrlRepository repository;

    @Value("${api.base-url}")
    private String baseUrl;

    @Test
    void create_Url_With_Valid_Link_Returns_UrlDTO() {
        String link = "https://www.example.com";
        String mockCode = "4bcd3f";

        Url url = new Url(mockCode, link);
        when(urlEncoder.getShortCode(link)).thenReturn(mockCode);
        when(repository.save(url)).thenReturn(url);
        UrlDTO res = service.createUrl(link);

        assertNotNull(res);
        assertNotEquals(res.longUrl(), res.shortUrl());
    }

    @Test
    void create_Url_With_Invalid_Link_Propagates_Exception() {
        String link = "myapp.company";

        when(urlEncoder.getShortCode(link)).thenThrow(new InvalidUrlException("Invalid url"));

        assertThatThrownBy(() -> urlEncoder.getShortCode(link))
                .isInstanceOf(InvalidUrlException.class)
                .hasMessageContaining("Invalid url");
    }

    @Test
    void getUrl_With_Existing_Code_Returns_Url() {
        String link = "https://example.com";
        String code = "5h0rtc";
        Url url = new Url(code, link);

        when(repository.findById(code)).thenReturn(Optional.of(url));

        UrlDTO res = service.getUrl(code);
        assertNotNull(res);
        assertNotEquals(res.longUrl(), res.shortUrl());
        assertEquals(link, res.longUrl());
    }

    @Test
    void getUrl_With_Non_Existing_Code_Throws_Exception() {
        String link = "https://example.com";
        String code = "aaaaaa";
        Url url = new Url(code, link);

        when(repository.findById(code)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.getUrl(code));
    }

    @Test
    void deleteUrl_With_Existing_Short_Url_Should_Call_Delete() {
        String code = "aeiou1";
        String link = "www.example.com";
        Url url = new Url(code, link);

        when(repository.findById(code)).thenReturn(Optional.of(url));
        service.deleteUrl(code);

        verify(repository).delete(url);
    }

    @Test
    void deleteUrl_With_NonExisting_Short_Url_Should_Throw_Exception() {
        String code = "ooooooo";

        when(repository.findById(code)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.deleteUrl(code))
                .isInstanceOf(UrlNotFoundException.class)
                .hasMessageContaining("Url not found.");

        verify(repository, never()).delete(any());
    }
}