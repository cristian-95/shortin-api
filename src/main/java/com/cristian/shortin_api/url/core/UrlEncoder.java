package com.cristian.shortin_api.url.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UrlEncoder {

    @Value("${encoder.alphabet}")
    private static String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Value("${encoder.base}")
    private static int BASE = 62;

    @Value("${encoder.length}")
    private static int LENGTH = 6;

    @Value("${encoder.algorithm}")
    private static String ALGORITHM = "SHA-256";

    public String getShortCode(String input) {
        return encode(input);
    }

    private static String encode(String input) {
        byte[] hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("Algorithm " + ALGORITHM + " not supported");
        }

        BigInteger hashNumber = new BigInteger(1, hash);
        BigInteger modulus = BigInteger.valueOf(BASE).pow(LENGTH);
        BigInteger number = hashNumber.mod(modulus);

        return toBase(number);
    }

    private static String toBase(BigInteger number) {
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
