package com.company.makepub.app.usecase;

import com.company.makepub.app.domain.ScriptureAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DetectScriptureTest {

    private static String html;
    private DetectScripture sut = new DetectScripture();
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
                """;
        expected = List.of(
                new ScriptureAddress("Salmos", 150, 118),
                new ScriptureAddress("Salmos", 150, 1),
                new ScriptureAddress("Salmos", 150, 11),
                new ScriptureAddress("Salmos", 25, 11),
                new ScriptureAddress("Salmos", 3, 11),
                new ScriptureAddress("Salmos", 3, 11),
                new ScriptureAddress("Salmos", 3, 11),
                new ScriptureAddress("Salmos", 3, 11),
                new ScriptureAddress("Salmos", 3, 11),
                new ScriptureAddress("Salmos", 10, 29),
                new ScriptureAddress("Salmos", 8, 38),
                new ScriptureAddress("Salmos", 21, 3),
                new ScriptureAddress("Salmos", 9, 5),
                new ScriptureAddress("Salmos", 24, 15),
                new ScriptureAddress("Jeremias", 20, 9),
                new ScriptureAddress("João", 17, 3)
        );
    }

    @Test
    @DisplayName("Should get the correct book name")
    void test1() {
        List<ScriptureAddress> actual = sut.execute(html);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).book(), actual.get(i).book());
        }
    }

    @Test
    @DisplayName("Should get the correct chapter")
    void test2() {
        List<ScriptureAddress> actual = sut.execute(html);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).chapter(), actual.get(i).chapter());
        }
    }
}