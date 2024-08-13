package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DetectScriptureTest {


    DetectScripture init(String html) {
        return new DetectScripture(html);
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
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link Salmos 10:<a href="#Salmos 10:1">1</a>
                """;
            String actualHtml = sut.getHtml();
            assertEquals(expectedHtml, actualHtml);
        }
        @Test
        @DisplayName("Link Salmo 10:1, 2")
        void test3f(TestInfo testInfo) {
            var sut = init(testInfo.getDisplayName());
            sut.execute();
            String expectedHtml = """
                Link Salmos 10:<a href="#Salmos 10:1">1</a>, <a href="#Salmos 10:2">2</a>
                """;
            String actualHtml = sut.getHtml();
            assertEquals(expectedHtml, actualHtml);
        }
    }
}