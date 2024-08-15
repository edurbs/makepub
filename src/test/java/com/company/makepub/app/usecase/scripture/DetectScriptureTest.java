package com.company.makepub.app.usecase.scripture;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetectScriptureTest {

    @CanIgnoreReturnValue
    DetectScripture init(String html) {
        Matcher matcher = new MakeRegex().getMatcher(html);
        matcher.find();
        return new DetectScripture(matcher, "" );
    }

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


    @Test
    @DisplayName("Detect 2 PED. 3:9.")
    void testUpperCase(TestInfo testInfo) {
        var sut = init(testInfo.getDisplayName());
        List<ScriptureAddress> actual = sut.execute();
        List<ScriptureAddress> expected = List.of(
                new ScriptureAddress(Book.BOOK_61_2PE, 3, 9, 0)
        );
        assertEquals(expected, actual);
    }


}