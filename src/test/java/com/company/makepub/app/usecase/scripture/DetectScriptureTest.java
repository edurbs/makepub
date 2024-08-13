package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import com.company.makepub.app.gateway.UUIDGenerator;
import com.company.makepub.utils.uuid.MyUUIDGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DetectScriptureTest {

    private final String TAG_A_PREFIX = "<a epub:type=\"noteref\" href=\"#uuid\">";

    @Mock
    private UUIDGenerator mockUUIDGenerator;

    DetectScripture init(String html) {
        return new DetectScripture(html, mockUUIDGenerator);
    }

    void setupMockUUIDGenerator(String uuid) {
        Mockito.when(mockUUIDGenerator.generate())
                .thenReturn(uuid);
    }

    void setupMockUUIDGenerator() {
        setupMockUUIDGenerator("uuid");
    }

    @Nested
    class DetectScriptureTests{
        @Test
        @DisplayName("Detect Salmo 10:1")
        void test2(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,0));
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1, 2")
        void test3(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 2,0)
            );
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1, 2, 4")
        void test4(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 2,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 4,0)
            );
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1, 2, 5-8, 10")
        void test4a(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 2,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 5,8),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 10,0)
            );
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1, 2, 5-8, 10, 12-14")
        void test4b(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 2,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 5,8),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 10,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 12,14)
            );
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1-3, 5-8, 10, 12")
        void test4c(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1,3),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 5,8),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 10,0),
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 12,0)
            );
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Detect Salmo 10:1-4")
        void test5(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            List<ScriptureAddress> actual = sut.execute();
            List<ScriptureAddress> expected = List.of(
                    new ScriptureAddress(Book.BOOK_19_PSA, 10, 1, 4)
            );
            assertEquals(expected, actual);
        }
    }

    @Nested
    class LinkScriptureTests{
        @Test
        @DisplayName("Link Salmo 10:1")
        void test2f(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1, 2")
        void test3f(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1, 2</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1-3")
        void test4f(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1-3</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1-3, 5-8, 10, 12")
        void test5f(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1-3, 5-8, 10, 12</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1; 11:1")
        void test6l(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1</a>;%<s 11:1</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3")
        void test7l(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3; Mateus 24:14")
        void test8l(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>; %<sMateus 24:14</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

        @Test
        @DisplayName("Link Salmo 10:1, 3-6, 10, 15-19; 11:10; 12:1-4, 6; 13:1-3; Mateus 24:14; 25:1-3")
        void test9l(TestInfo testInfo) {
            setupMockUUIDGenerator();
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link %sSalmo 10:1, 3-6, 10, 15-19</a>;%<s 11:10</a>;%<s 12:1-4, 6</a>;%<s 13:1-3</a>; %<sMateus 24:14</a>;%<s 25:1-3</a>
                """.formatted(TAG_A_PREFIX).trim();
            String actualHtml = sut.getLinkedHtml();
            assertEquals(expectedHtml, actualHtml);
        }

    }
}