package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.Book;
import com.company.makepub.app.domain.ScriptureAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DetectScriptureTest {

    private static String html;
    private final DetectScripture sut = new DetectScripture();
    private static List<ScriptureAddress> expected;

    @BeforeAll
    static void setUp() {
        html = """
                (Salmos 150:118)\s
                (Salmos 150:1)\s
                (Salmos 150:11)
                (Salmos 25:11)
                (Salmos 3:11)
                (Salmos 3:11.)
                (João 17:3)
                (Salmos 3:11, 12, 14)
                (Salmos 3:11-13)
                (Salmos 3:11; 4:12;)
                (Salmos 10:29, 30;  5:28, 29; Salmos 8:38, 39; Salmos  21:3, 4)
                Veja também Jeremias 20:9.
                (Salmos 9:5, 10; Salmos 24:15)
                (Sal. 83:18)
                (Mt 24:14)
                (Mat. 2:1)
                (1 Sam. 5:1)
                (2Sa 8:3)
                """;
        expected = List.of(
                new ScriptureAddress(Book.BOOK_09_1SA, 5, 1),
                new ScriptureAddress(Book.BOOK_10_2SA, 8, 3),
                new ScriptureAddress(Book.BOOK_19_PSA, 150, 118),
                new ScriptureAddress(Book.BOOK_19_PSA, 150, 1),
                new ScriptureAddress(Book.BOOK_19_PSA, 150, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 25, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 3, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 3, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 3, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 3, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 3, 11),
                new ScriptureAddress(Book.BOOK_19_PSA, 10, 29),
                new ScriptureAddress(Book.BOOK_19_PSA, 8, 38),
                new ScriptureAddress(Book.BOOK_19_PSA, 21, 3),
                new ScriptureAddress(Book.BOOK_19_PSA, 9, 5),
                new ScriptureAddress(Book.BOOK_19_PSA, 24, 15),
                new ScriptureAddress(Book.BOOK_19_PSA, 83, 18),
                new ScriptureAddress(Book.BOOK_24_JER, 20, 9),
                new ScriptureAddress(Book.BOOK_40_MAT, 24, 14),
                new ScriptureAddress(Book.BOOK_40_MAT, 2, 1),
                new ScriptureAddress(Book.BOOK_43_JOH, 17, 3)
        );
    }

    @Test
    @DisplayName("Should get the correct addresses")
    void test1() {
        List<ScriptureAddress> actual = sut.execute(html);

        assertEquals(expected, actual);
    }

}