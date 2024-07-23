package com.company.makepub.app.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScriptureEarthBookNameTest {

    @Test
    void getName() {
        assertEquals("3JN", ScriptureEarthBookName.BOOK_3JN.getName());
    }

    @Test
    @DisplayName("Should get correct enum from 3 digit String")
    void fromString() {
        String string = "3JN";
        assertEquals(
                ScriptureEarthBookName.BOOK_3JN,
                ScriptureEarthBookName.fromScriptureEarthString(string));
    }

    @Test
    @DisplayName("Should get correct scripture earth format from meps format")
    void fromMepsFormat() {
        assertEquals(
                ScriptureEarthBookName.BOOK_3JN,
                ScriptureEarthBookName.fromMepsFormatEnum(BookName.BOOK_64_3JO));
    }

    @Test
    @DisplayName("Should get correct scripture earth format from meps format")
    void getMepsFormatFromScriptureEarth() {
        assertEquals(
                BookName.BOOK_64_3JO,
                ScriptureEarthBookName.getMepsFormatFromScriptureEarthEnum(ScriptureEarthBookName.BOOK_3JN));
    }

    @Test
    @DisplayName("Should get 66 values")
    void values() {
        int size = ScriptureEarthBookName.values().length;
        assertEquals(66, size);
    }
}