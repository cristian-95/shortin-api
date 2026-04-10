package com.cristian.shortin_api.url.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlEncoderTest {

    private UrlEncoder encoder = new UrlEncoder();

    @Test
    void getShortCode_With_Valid_Link_Returns_Valid_Code() {
        String VALID_LINK = "www.example.com";
        var code = encoder.getShortCode(VALID_LINK);
        assertNotNull(code);
        assertEquals(6, code.length());
    }

    @Test
    void getShortCode_With_Different_Link_Return_Different_Codes() {
        String LINK1 = "www.example.com";
        String LINK2 = "www.another.com";
        var code1 = encoder.getShortCode(LINK1);
        var code2 = encoder.getShortCode(LINK2);
        assertNotNull(code1);
        assertEquals(6, code1.length());
        assertNotNull(code2);
        assertEquals(6, code2.length());
        assertNotEquals(code1, code2);
    }

    @Test
    void getShortCode_With_Empty_Link_Throws_Runtime_Exception() {
        assertThrows(RuntimeException.class, () -> encoder.getShortCode(""));
    }

    @Test
    void getShortCode_With_Invalid_Link_Throws_Runtime_Exception() {
        String INVALID_LINK = "some invalid text";
        assertThrows(RuntimeException.class, () -> encoder.getShortCode(INVALID_LINK));
    }
}
