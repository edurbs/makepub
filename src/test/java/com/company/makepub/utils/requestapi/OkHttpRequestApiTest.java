package com.company.makepub.utils.requestapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest()
class OkHttpRequestApiTest {

    public static final String LOCALHOST_8080_API = "http://localhost:8080/api";
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer();
        WireMock.configureFor("localhost", 8080);
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should convert the text")
    void getText() {
        wireMockServer.stubFor(post(urlPathEqualTo("/api"))
                .willReturn(aResponse()
                        .withBody("Jesus 2")));
        String text = "Zezu 2";
        String actual = new OkHttpRequestApi().get(LOCALHOST_8080_API, text);
        String expected = "Jesus 2";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return blank when text is null")
    void testNullText(){
        assertEquals("", new OkHttpRequestApi().get(LOCALHOST_8080_API, null));
    }

    @Test
    @DisplayName("Should return blank when text is blank")
    void testBlankText(){
        assertEquals("", new OkHttpRequestApi().get(LOCALHOST_8080_API, " "));
    }
}