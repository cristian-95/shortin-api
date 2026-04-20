package com.cristian.shortin_api.url.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.InvalidUrlException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UrlEncoder {

    @Value("${encoder.alphabet}")
    private String ALPHABET;

    @Value("${encoder.base}")
    private int BASE;

    @Value("${encoder.length}")
    private int LENGTH;

    @Value("${encoder.algorithm}")
    private String ALGORITHM;

    public String getShortCode(String input) {
        return encode(input);
    }

    private String encode(String input) {
        String URL_REGEX = "((ht|f)tp(s)?://)?(w{0,3}\\.)?[a-zA-Z0-9_\\-.:#/~}]+(\\.[a-zA-Z]{1,4})(/[a-zA-Z0-9_\\-.:#/~}]*)?";
        if (!input.matches(URL_REGEX)) {
            throw new InvalidUrlException("Invalid url.");
        }

        byte[] hash = getHash(input);

        BigInteger hashNumber = new BigInteger(1, hash);
        BigInteger modulus = BigInteger.valueOf(BASE).pow(LENGTH);
        BigInteger number = hashNumber.mod(modulus);

        return toBase(number);
    }

    private byte[] getHash(String input) {
        byte[] hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException("Algorithm " + ALGORITHM + " not supported");
        }
        return hash;
    }

    private String toBase(BigInteger number) {
        if (number.equals(BigInteger.ZERO)) {
            throw new RuntimeException(" Untreated exception");
        }

        StringBuilder stringBuilder = new StringBuilder();
        BigInteger num = number;
        while (num.compareTo(BigInteger.ZERO) > 0) {
            int remainder = num.mod(BigInteger.valueOf(BASE)).intValue();
            stringBuilder.append(ALPHABET.charAt(remainder));
            num = num.divide(BigInteger.valueOf(BASE));
        }
        return stringBuilder.reverse().toString();
    }
}
