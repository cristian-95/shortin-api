package com.cristian.shortin_api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "urls")
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class    Url {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "long_url", nullable = false, unique = true)
    private String longUrl;
    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;
}
