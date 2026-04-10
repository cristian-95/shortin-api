package com.cristian.shortin_api.url.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "urls")
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class    Url {
    @Id
    @Column(unique = true)
    private String code;
    @Column(name = "long_url", nullable = false, unique = true)
    private String longUrl;

}