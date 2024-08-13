package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.gateway.UUIDGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LinkScriptureTest {
    private final String TAG_A_PREFIX = "<a epub:type=\"noteref\" href=\"#uuid\">";

    @Mock
    private UUIDGenerator mockUUIDGenerator;

    LinkScripture init(String html) {
        return new LinkScripture(new MakeRegex(html).execute(), mockUUIDGenerator);
    }

    void setupMockUUIDGenerator() {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn("uuid");
    }


    @Test
    @DisplayName("Link Salmo 10:1")
    void test2f(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 2")
    void test3f(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1, 2</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1-3")
    void test4f(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1-3</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1-3, 5-8, 10, 12")
    void test5f(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1-3, 5-8, 10, 12</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1; 11:1")
    void test6l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1</a>;%<s 11:1</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3")
    void test7l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3; Mateus 24:14")
    void test8l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>; %<sMateus 24:14</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }

    @Test
    @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3; Mateus 24:14; 25:1-3")
    void test9l(TestInfo testInfo) {
        setupMockUUIDGenerator();
        var sut = init(testInfo.getDisplayName());
        String expectedHtml = """
            Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>; %<sMateus 24:14</a>;%<s 25:1-3</a>
            """.formatted(TAG_A_PREFIX).trim();
        String actualHtml = sut.execute();
        assertEquals(expectedHtml, actualHtml);
    }


}
